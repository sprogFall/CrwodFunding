package org.fall.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import org.fall.constant.CrowdConstant;
import org.fall.exception.LoginFailedException;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CrowdUtil {

    /**
     * 判断请求类型
     * @param request
     * @return true=json请求;false=普通页面请求
     */
    public static boolean judgeRequestType(HttpServletRequest request){
        String accept = request.getHeader("Accept");
        String header = request.getHeader("X-Requested-With");
        return (accept != null && accept.contains("application/json"))
                ||
                (header != null && header.equals("XMLHttpRequest"));
    }


    /**
     * 此方法是用于给字符串进行md5加密的工具方法
     * @return 进行md5加密后的结果
     * @param source 传入要加密的内容
     */
    public static String md5(String source){

        if (source == null || source.length() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        try {
            //表示算法名
            String algorithm = "md5";

            //得到MessageDigest对象，设置加密方式为md5
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            //将获得的明文字符串转换为字节数组
            byte[] input = source.getBytes();

            //对转换得到的字节数组进行md5加密
            byte[] output = messageDigest.digest(input);

            //设置BigInteger的signum
            //signum : -1表示负数、0表示零、1表示正数
            int signum = 1;

            //将字节数组转换成Big Integer
            BigInteger bigInteger = new BigInteger(signum,output);

            //设置将bigInteger的值按照16进制转换成字符串，最后全部转换成大写，得到最后的加密结果
            int radix = 16;
            String encoded = bigInteger.toString(radix).toUpperCase();

            //返回加密后的字符串
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //触发异常则返回null
        return null;
    }


    /**
     *
     * @param host  请求的地址
     * @param path  请求的后缀
     * @param appCode   购入的api的appCode
     * @param phoneNum  发送验证码的目的号码
     * @param sign      签名编号
     * @param skin      模板编号
     * @return          发送成功则返回发送的验证码，放在ResultEntity中，失败则返回失败的ResultEntity
     */
    public static ResultEntity<String> sendCodeByShortMessage(
            String host,
            String path,
            String appCode,
            String phoneNum,
            String sign,
            String skin
    ){
        // 生成验证码
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++){
            int random = (int)(Math.random()*10);
            builder.append(random);
        }
        String param = builder.toString();  // 【4】请求参数，详见文档描述
        String urlSend = host + path + "?param=" + param + "&phone=" + phoneNum + "&sign=" + sign + "&skin=" + skin;  // 【5】拼接请求链接
        try {
            URL url = new URL(urlSend);
            HttpURLConnection httpURLCon = (HttpURLConnection) url.openConnection();
            httpURLCon.setRequestProperty("Authorization", "APPCODE " + appCode);// 格式Authorization:APPCODE (中间是英文空格)
            int httpCode = httpURLCon.getResponseCode();
            if (httpCode == 200) {
                String json = read(httpURLCon.getInputStream());
                System.out.println("正常请求计费(其他均不计费)");
                System.out.println("获取返回的json:");
                System.out.print(json);
                return ResultEntity.successWithData(param);
            } else {
                Map<String, List<String>> map = httpURLCon.getHeaderFields();
                String error = map.get("X-Ca-Error-Message").get(0);
                if (httpCode == 400 && error.equals("Invalid AppCode `not exists`")) {
                    return ResultEntity.failed("AppCode错误 ");
                } else if (httpCode == 400 && error.equals("Invalid Url")) {
                    return ResultEntity.failed("请求的 Method、Path 或者环境错误");
                } else if (httpCode == 400 && error.equals("Invalid Param Location")) {
                    return ResultEntity.failed("参数错误");
                } else if (httpCode == 403 && error.equals("Unauthorized")) {
                    return ResultEntity.failed("服务未被授权（或URL和Path不正确）");
                } else if (httpCode == 403 && error.equals("Quota Exhausted")) {
                    return ResultEntity.failed("套餐包次数用完 ");
                } else {
                    return ResultEntity.failed("参数名错误 或 其他错误" + error);
                }
            }

        } catch (MalformedURLException e) {
            return ResultEntity.failed("URL格式错误");
        } catch (UnknownHostException e) {
            return ResultEntity.failed("URL地址错误");
        } catch (Exception e) {
             e.printStackTrace();
            return ResultEntity.failed("套餐包次数用完 ");
        }
    }


    /*
     * 读取返回结果
     */
    private static String read(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null) {
            line = new String(line.getBytes(), StandardCharsets.UTF_8);
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }



    public static ResultEntity<String> uploadFileToOSS(
            String endPoint,
            String accessKeyId,
            String accessKeySecret,
            InputStream inputStream,
            String bucketName,
            String bucketDomain,
            String originalName ){

        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endPoint,accessKeyId,accessKeySecret);

        // 生成上传文件的目录，按照日期来划分目录
        String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());

        // 生成上传文件在OSS服务器上保存的文件名,通过uuid生成随机uuid，将其中的“-”删去（替换成空字符串）
        String fileMainName = UUID.randomUUID().toString().replace("-", "");

        // 从原始文件名中获取文件扩展名
        String extensionName = originalName.substring(originalName.lastIndexOf("."));

        // 使用目录、文件主体名称、文件扩展名拼接得到对象名称
        String objectName = folderName + "/" + fileMainName + extensionName;


        try {
            // 调用OSS客户端对象的方法上传文件并获取响应结果数据
            PutObjectResult putObjectResult = ossClient.putObject(bucketName,objectName,inputStream);

            // 从响应结果中获取具体的响应消息
            ResponseMessage responseMessage = putObjectResult.getResponse();

            // 根据响应状态判断是否成功
            if (responseMessage == null) {
                // 拼接访问刚刚上传的文件的路径
                String ossFileAccessPath = bucketDomain + "/" + objectName;

                // 返回成功，并带上访问路径
                return ResultEntity.successWithData(ossFileAccessPath);
            }else {
                // 获取响应状态码
                int statusCode = responseMessage.getStatusCode();
                // 没有成功 获取错误消息
                String errorMessage = responseMessage.getErrorResponseAsString();

                return ResultEntity.failed("当前响应状态码=" + statusCode + " 错误消息=" + errorMessage);
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        } finally {
            // 关闭OSSClient
            ossClient.shutdown();
        }

    }





}

package org.fall.entity.po;

import java.util.ArrayList;
import java.util.List;

public class MemberPOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MemberPOExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andLoginAcctIsNull() {
            addCriterion("login_acct is null");
            return (Criteria) this;
        }

        public Criteria andLoginAcctIsNotNull() {
            addCriterion("login_acct is not null");
            return (Criteria) this;
        }

        public Criteria andLoginAcctEqualTo(String value) {
            addCriterion("login_acct =", value, "loginAcct");
            return (Criteria) this;
        }

        public Criteria andLoginAcctNotEqualTo(String value) {
            addCriterion("login_acct <>", value, "loginAcct");
            return (Criteria) this;
        }

        public Criteria andLoginAcctGreaterThan(String value) {
            addCriterion("login_acct >", value, "loginAcct");
            return (Criteria) this;
        }

        public Criteria andLoginAcctGreaterThanOrEqualTo(String value) {
            addCriterion("login_acct >=", value, "loginAcct");
            return (Criteria) this;
        }

        public Criteria andLoginAcctLessThan(String value) {
            addCriterion("login_acct <", value, "loginAcct");
            return (Criteria) this;
        }

        public Criteria andLoginAcctLessThanOrEqualTo(String value) {
            addCriterion("login_acct <=", value, "loginAcct");
            return (Criteria) this;
        }

        public Criteria andLoginAcctLike(String value) {
            addCriterion("login_acct like", value, "loginAcct");
            return (Criteria) this;
        }

        public Criteria andLoginAcctNotLike(String value) {
            addCriterion("login_acct not like", value, "loginAcct");
            return (Criteria) this;
        }

        public Criteria andLoginAcctIn(List<String> values) {
            addCriterion("login_acct in", values, "loginAcct");
            return (Criteria) this;
        }

        public Criteria andLoginAcctNotIn(List<String> values) {
            addCriterion("login_acct not in", values, "loginAcct");
            return (Criteria) this;
        }

        public Criteria andLoginAcctBetween(String value1, String value2) {
            addCriterion("login_acct between", value1, value2, "loginAcct");
            return (Criteria) this;
        }

        public Criteria andLoginAcctNotBetween(String value1, String value2) {
            addCriterion("login_acct not between", value1, value2, "loginAcct");
            return (Criteria) this;
        }

        public Criteria andUserPswdIsNull() {
            addCriterion("user_pswd is null");
            return (Criteria) this;
        }

        public Criteria andUserPswdIsNotNull() {
            addCriterion("user_pswd is not null");
            return (Criteria) this;
        }

        public Criteria andUserPswdEqualTo(String value) {
            addCriterion("user_pswd =", value, "userPswd");
            return (Criteria) this;
        }

        public Criteria andUserPswdNotEqualTo(String value) {
            addCriterion("user_pswd <>", value, "userPswd");
            return (Criteria) this;
        }

        public Criteria andUserPswdGreaterThan(String value) {
            addCriterion("user_pswd >", value, "userPswd");
            return (Criteria) this;
        }

        public Criteria andUserPswdGreaterThanOrEqualTo(String value) {
            addCriterion("user_pswd >=", value, "userPswd");
            return (Criteria) this;
        }

        public Criteria andUserPswdLessThan(String value) {
            addCriterion("user_pswd <", value, "userPswd");
            return (Criteria) this;
        }

        public Criteria andUserPswdLessThanOrEqualTo(String value) {
            addCriterion("user_pswd <=", value, "userPswd");
            return (Criteria) this;
        }

        public Criteria andUserPswdLike(String value) {
            addCriterion("user_pswd like", value, "userPswd");
            return (Criteria) this;
        }

        public Criteria andUserPswdNotLike(String value) {
            addCriterion("user_pswd not like", value, "userPswd");
            return (Criteria) this;
        }

        public Criteria andUserPswdIn(List<String> values) {
            addCriterion("user_pswd in", values, "userPswd");
            return (Criteria) this;
        }

        public Criteria andUserPswdNotIn(List<String> values) {
            addCriterion("user_pswd not in", values, "userPswd");
            return (Criteria) this;
        }

        public Criteria andUserPswdBetween(String value1, String value2) {
            addCriterion("user_pswd between", value1, value2, "userPswd");
            return (Criteria) this;
        }

        public Criteria andUserPswdNotBetween(String value1, String value2) {
            addCriterion("user_pswd not between", value1, value2, "userPswd");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andAuthstausIsNull() {
            addCriterion("authstaus is null");
            return (Criteria) this;
        }

        public Criteria andAuthstausIsNotNull() {
            addCriterion("authstaus is not null");
            return (Criteria) this;
        }

        public Criteria andAuthstausEqualTo(Integer value) {
            addCriterion("authstaus =", value, "authstaus");
            return (Criteria) this;
        }

        public Criteria andAuthstausNotEqualTo(Integer value) {
            addCriterion("authstaus <>", value, "authstaus");
            return (Criteria) this;
        }

        public Criteria andAuthstausGreaterThan(Integer value) {
            addCriterion("authstaus >", value, "authstaus");
            return (Criteria) this;
        }

        public Criteria andAuthstausGreaterThanOrEqualTo(Integer value) {
            addCriterion("authstaus >=", value, "authstaus");
            return (Criteria) this;
        }

        public Criteria andAuthstausLessThan(Integer value) {
            addCriterion("authstaus <", value, "authstaus");
            return (Criteria) this;
        }

        public Criteria andAuthstausLessThanOrEqualTo(Integer value) {
            addCriterion("authstaus <=", value, "authstaus");
            return (Criteria) this;
        }

        public Criteria andAuthstausIn(List<Integer> values) {
            addCriterion("authstaus in", values, "authstaus");
            return (Criteria) this;
        }

        public Criteria andAuthstausNotIn(List<Integer> values) {
            addCriterion("authstaus not in", values, "authstaus");
            return (Criteria) this;
        }

        public Criteria andAuthstausBetween(Integer value1, Integer value2) {
            addCriterion("authstaus between", value1, value2, "authstaus");
            return (Criteria) this;
        }

        public Criteria andAuthstausNotBetween(Integer value1, Integer value2) {
            addCriterion("authstaus not between", value1, value2, "authstaus");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNull() {
            addCriterion("user_type is null");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNotNull() {
            addCriterion("user_type is not null");
            return (Criteria) this;
        }

        public Criteria andUserTypeEqualTo(Integer value) {
            addCriterion("user_type =", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotEqualTo(Integer value) {
            addCriterion("user_type <>", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThan(Integer value) {
            addCriterion("user_type >", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_type >=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThan(Integer value) {
            addCriterion("user_type <", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThanOrEqualTo(Integer value) {
            addCriterion("user_type <=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeIn(List<Integer> values) {
            addCriterion("user_type in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotIn(List<Integer> values) {
            addCriterion("user_type not in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeBetween(Integer value1, Integer value2) {
            addCriterion("user_type between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("user_type not between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andRealNameIsNull() {
            addCriterion("real_name is null");
            return (Criteria) this;
        }

        public Criteria andRealNameIsNotNull() {
            addCriterion("real_name is not null");
            return (Criteria) this;
        }

        public Criteria andRealNameEqualTo(String value) {
            addCriterion("real_name =", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameNotEqualTo(String value) {
            addCriterion("real_name <>", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameGreaterThan(String value) {
            addCriterion("real_name >", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameGreaterThanOrEqualTo(String value) {
            addCriterion("real_name >=", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameLessThan(String value) {
            addCriterion("real_name <", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameLessThanOrEqualTo(String value) {
            addCriterion("real_name <=", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameLike(String value) {
            addCriterion("real_name like", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameNotLike(String value) {
            addCriterion("real_name not like", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameIn(List<String> values) {
            addCriterion("real_name in", values, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameNotIn(List<String> values) {
            addCriterion("real_name not in", values, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameBetween(String value1, String value2) {
            addCriterion("real_name between", value1, value2, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameNotBetween(String value1, String value2) {
            addCriterion("real_name not between", value1, value2, "realName");
            return (Criteria) this;
        }

        public Criteria andCardNumIsNull() {
            addCriterion("card_num is null");
            return (Criteria) this;
        }

        public Criteria andCardNumIsNotNull() {
            addCriterion("card_num is not null");
            return (Criteria) this;
        }

        public Criteria andCardNumEqualTo(String value) {
            addCriterion("card_num =", value, "cardNum");
            return (Criteria) this;
        }

        public Criteria andCardNumNotEqualTo(String value) {
            addCriterion("card_num <>", value, "cardNum");
            return (Criteria) this;
        }

        public Criteria andCardNumGreaterThan(String value) {
            addCriterion("card_num >", value, "cardNum");
            return (Criteria) this;
        }

        public Criteria andCardNumGreaterThanOrEqualTo(String value) {
            addCriterion("card_num >=", value, "cardNum");
            return (Criteria) this;
        }

        public Criteria andCardNumLessThan(String value) {
            addCriterion("card_num <", value, "cardNum");
            return (Criteria) this;
        }

        public Criteria andCardNumLessThanOrEqualTo(String value) {
            addCriterion("card_num <=", value, "cardNum");
            return (Criteria) this;
        }

        public Criteria andCardNumLike(String value) {
            addCriterion("card_num like", value, "cardNum");
            return (Criteria) this;
        }

        public Criteria andCardNumNotLike(String value) {
            addCriterion("card_num not like", value, "cardNum");
            return (Criteria) this;
        }

        public Criteria andCardNumIn(List<String> values) {
            addCriterion("card_num in", values, "cardNum");
            return (Criteria) this;
        }

        public Criteria andCardNumNotIn(List<String> values) {
            addCriterion("card_num not in", values, "cardNum");
            return (Criteria) this;
        }

        public Criteria andCardNumBetween(String value1, String value2) {
            addCriterion("card_num between", value1, value2, "cardNum");
            return (Criteria) this;
        }

        public Criteria andCardNumNotBetween(String value1, String value2) {
            addCriterion("card_num not between", value1, value2, "cardNum");
            return (Criteria) this;
        }

        public Criteria andAcctTypeIsNull() {
            addCriterion("acct_type is null");
            return (Criteria) this;
        }

        public Criteria andAcctTypeIsNotNull() {
            addCriterion("acct_type is not null");
            return (Criteria) this;
        }

        public Criteria andAcctTypeEqualTo(Integer value) {
            addCriterion("acct_type =", value, "acctType");
            return (Criteria) this;
        }

        public Criteria andAcctTypeNotEqualTo(Integer value) {
            addCriterion("acct_type <>", value, "acctType");
            return (Criteria) this;
        }

        public Criteria andAcctTypeGreaterThan(Integer value) {
            addCriterion("acct_type >", value, "acctType");
            return (Criteria) this;
        }

        public Criteria andAcctTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("acct_type >=", value, "acctType");
            return (Criteria) this;
        }

        public Criteria andAcctTypeLessThan(Integer value) {
            addCriterion("acct_type <", value, "acctType");
            return (Criteria) this;
        }

        public Criteria andAcctTypeLessThanOrEqualTo(Integer value) {
            addCriterion("acct_type <=", value, "acctType");
            return (Criteria) this;
        }

        public Criteria andAcctTypeIn(List<Integer> values) {
            addCriterion("acct_type in", values, "acctType");
            return (Criteria) this;
        }

        public Criteria andAcctTypeNotIn(List<Integer> values) {
            addCriterion("acct_type not in", values, "acctType");
            return (Criteria) this;
        }

        public Criteria andAcctTypeBetween(Integer value1, Integer value2) {
            addCriterion("acct_type between", value1, value2, "acctType");
            return (Criteria) this;
        }

        public Criteria andAcctTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("acct_type not between", value1, value2, "acctType");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
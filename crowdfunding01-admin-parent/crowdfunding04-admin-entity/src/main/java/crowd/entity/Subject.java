package crowd.entity;

public class Subject {
    private String subjectName;
    private String score;

    public Subject() {
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectName='" + subjectName + '\'' +
                ", score='" + score + '\'' +
                '}';
    }

    public Subject(String subjectName, String score) {
        this.subjectName = subjectName;
        this.score = score;
    }
}

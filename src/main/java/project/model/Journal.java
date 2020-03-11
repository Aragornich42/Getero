package project.model;

public class Journal {

    private int id;
    private Integer studentId;
    private Integer studyPlanId;
    private Integer inTime;
    private Integer count;
    private Integer markId;

    public Journal() {}

    public Journal(int id, int studentId, int studyPlanId,
                   int inTime, int count, int markId) {
        this.id = id;
        this.studentId = studentId;
        this.studyPlanId = studyPlanId;
        this.inTime = inTime;
        this.count = count;
        this.markId = markId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Integer getStudyPlanId() {
        return studyPlanId;
    }

    public void setStudyPlanId(int studyPlanId) {
        this.studyPlanId = studyPlanId;
    }

    public Integer isInTime() {
        return inTime;
    }

    public void setInTime(Integer inTime) {
        this.inTime = inTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Integer getMarkId() {
        return markId;
    }

    public void setMarkId(int markId) {
        this.markId = markId;
    }

}

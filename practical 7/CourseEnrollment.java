public class CourseEnrollment {
    @Column(name = "STUDENT_ID")
    private String studentId;

    @Column(name = "STUDENT_NAME")
    private String studentName;

    @Column(name = "CREDITS")
    private int credits;

    @Column(name = "LAB_BATCH")
    private String labBatch = "UNASSIGNED";

    @Override
    public String toString() {
        return "CourseEnrollment[studentId='" + studentId + "', studentName='" + studentName
                + "', credits=" + credits + ", labBatch='" + labBatch + "']";
    }
}

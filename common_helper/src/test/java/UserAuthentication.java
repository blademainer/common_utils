import com.xiongyingqi.jackson.annotation.AllowProperty;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/4/28 0028.
 */
public class UserAuthentication {
    private Integer authenticationId;
    private Byte isEmailVerify;
    private Byte isPhoneVerify;
    private String verifyQuestionOne;
    private String verifyQuestionOneAnswer;
    private String verifyQuestionTwo;
    private String verifyQuestionTwoAnswer;
    private String verifyQuestionThree;
    private String verifyQuestionThreeAnswer;
    private boolean isDeleted;

    private String getName;

    public String getGetName() {
        return getName;
    }

    public void setGetName(String getName) {
        this.getName = getName;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @AllowProperty(pojo = UserAuthentication.class, name = "asfasf")
    public Integer getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(Integer authenticationId) {
        this.authenticationId = authenticationId;
    }

    @AllowProperty(pojo = UserAuthentication.class, name = "isEmailVerify")
    public Byte getIsEmailVerify() {
        return isEmailVerify;
    }

    public void setIsEmailVerify(Byte isEmailVerify) {
        this.isEmailVerify = isEmailVerify;
    }

    @AllowProperty(pojo = UserAuthentication.class, name = "isPhoneVerify")
    public Byte getIsPhoneVerify() {
        return isPhoneVerify;
    }

    public void setIsPhoneVerify(Byte isPhoneVerify) {
        this.isPhoneVerify = isPhoneVerify;
    }

    @AllowProperty(pojo = UserAuthentication.class, name = "verifyQuestionOne")
    public String getVerifyQuestionOne() {
        return verifyQuestionOne;
    }

    public void setVerifyQuestionOne(String verifyQuestionOne) {
        this.verifyQuestionOne = verifyQuestionOne;
    }

    @AllowProperty(pojo = UserAuthentication.class, name = "verifyQuestionOneAnswer")
    public String getVerifyQuestionOneAnswer() {
        return verifyQuestionOneAnswer;
    }

    public void setVerifyQuestionOneAnswer(String verifyQuestionOneAnswer) {
        this.verifyQuestionOneAnswer = verifyQuestionOneAnswer;
    }

    @AllowProperty(pojo = UserAuthentication.class, name = "verifyQuestionTwo")
    public String getVerifyQuestionTwo() {
        return verifyQuestionTwo;
    }

    public void setVerifyQuestionTwo(String verifyQuestionTwo) {
        this.verifyQuestionTwo = verifyQuestionTwo;
    }

    @AllowProperty(pojo = UserAuthentication.class, name = "verifyQuestionTwoAnswer")
    public String getVerifyQuestionTwoAnswer() {
        return verifyQuestionTwoAnswer;
    }

    public void setVerifyQuestionTwoAnswer(String verifyQuestionTwoAnswer) {
        this.verifyQuestionTwoAnswer = verifyQuestionTwoAnswer;
    }

    @AllowProperty(pojo = UserAuthentication.class, name = "verifyQuestionThree")
    public String getVerifyQuestionThree() {
        return verifyQuestionThree;
    }

    public void setVerifyQuestionThree(String verifyQuestionThree) {
        this.verifyQuestionThree = verifyQuestionThree;
    }

    @AllowProperty(pojo = UserAuthentication.class, name = "verifyQuestionThreeAnswer")
    public String getVerifyQuestionThreeAnswer() {
        return verifyQuestionThreeAnswer;
    }

    public void setVerifyQuestionThreeAnswer(String verifyQuestionThreeAnswer) {
        this.verifyQuestionThreeAnswer = verifyQuestionThreeAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAuthentication that = (UserAuthentication) o;

        if (authenticationId != null ? !authenticationId.equals(that.authenticationId) : that.authenticationId != null)
            return false;
        if (isEmailVerify != null ? !isEmailVerify.equals(that.isEmailVerify) : that.isEmailVerify != null)
            return false;
        if (isPhoneVerify != null ? !isPhoneVerify.equals(that.isPhoneVerify) : that.isPhoneVerify != null)
            return false;
        if (verifyQuestionOne != null ? !verifyQuestionOne.equals(that.verifyQuestionOne) : that.verifyQuestionOne != null)
            return false;
        if (verifyQuestionOneAnswer != null ? !verifyQuestionOneAnswer.equals(that.verifyQuestionOneAnswer) : that.verifyQuestionOneAnswer != null)
            return false;
        if (verifyQuestionThree != null ? !verifyQuestionThree.equals(that.verifyQuestionThree) : that.verifyQuestionThree != null)
            return false;
        if (verifyQuestionThreeAnswer != null ? !verifyQuestionThreeAnswer.equals(that.verifyQuestionThreeAnswer) : that.verifyQuestionThreeAnswer != null)
            return false;
        if (verifyQuestionTwo != null ? !verifyQuestionTwo.equals(that.verifyQuestionTwo) : that.verifyQuestionTwo != null)
            return false;
        if (verifyQuestionTwoAnswer != null ? !verifyQuestionTwoAnswer.equals(that.verifyQuestionTwoAnswer) : that.verifyQuestionTwoAnswer != null)
            return false;

        return true;
    }

    public static void main(String[] args) {

//        Field[] fields = UserAuthentication.class.getDeclaredFields();
//        for (Field field : fields) {
//            Collection<Annotation> annotations = (Collection<Annotation>) AnnotationHelper.readAnnotationsOnField(field);
//            for (Annotation annotation : annotations) {
//                EntityHelper.print(annotation);
//            }
//        }
    }
}

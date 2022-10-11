package guru.qa.domain;

public class Teacher {
    private String name;
    private Boolean isGoodTeacher;
    private Integer age;
    private Passport passport;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isGoodTeacher() {
        return isGoodTeacher;
    }

    public void setGoodTeacher(Boolean goodTeacher) {
        isGoodTeacher = goodTeacher;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public static class Passport{
        private Integer serial;
        private Integer number;

        public Integer getSerial() {
            return serial;
        }

        public void setSerial(Integer serial) {
            this.serial = serial;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }
    }
}

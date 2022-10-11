package guru.qa.domain;

public class CarOwner {
    private String name;
    private Integer age;
    private Boolean onCredit;
    private Cars cars;

    public static class Cars {
        private String name;
        private String models;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getModels() {
            return models;
        }

        public void setModels(String models) {
            this.models = models;
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getOnCredit() {
        return onCredit;
    }

    public void setOnCredit(Boolean onCredit) {
        this.onCredit = onCredit;
    }

    public Cars getCars() {
        return cars;
    }

    public void setCars(Cars cars) {
        this.cars = cars;
    }
}

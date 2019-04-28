package net.random_development.monitor.data;

public class InfluxConfig {

    private String url;
    private String username;
    private String password;

    public InfluxConfig(String username, String username1, String password) {
        this.username = username;
        this.username = username1;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

package engine.core;

public class SingletonEngineConfiguration extends EngineConfiguration {

    private String jdbcUrl, jdbcDrive, jdbcUsername,jdbcPassword;

    public SingletonEngineConfiguration() {
        super();
    }

    public SingletonEngineConfiguration(String jdbcUrl, String jdbcDrive, String jdbcUsername, String jdbcPassword) {
        super();
        this.jdbcUrl = jdbcUrl;
        this.jdbcDrive = jdbcDrive;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcDrive() {
        return jdbcDrive;
    }

    public void setJdbcDrive(String jdbcDrive) {
        this.jdbcDrive = jdbcDrive;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {

        this.jdbcPassword = jdbcPassword;
    }

    public WorkFlowEngine buildProcessEngine() {
        init();
        WorkFlowEngine.setWorkFlowEngine(new WorkFlowEngine(this)) ;
        System.out.println("engine created!");
        return WorkFlowEngine.getWorkFlowEngine();
    }
    private void init(){
        //清空表 和 建表

    }
}

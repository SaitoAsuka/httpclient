package net.httpclient.RestFulAction;

public class flowTable {
    private String switchAdd;
    private String name;
    private String cookieMask;
    private String actions;
    private String cookie;
    private String idleTimeoutSec;
    private String flags;
    private String in_port;
    private String hardTimeoutSec;
    private String outGroup;
    private String priority;
    private String outPort;
    private String version;
    private String command;

    public void set(String key, String value) {
        switch (key) {
            case "cookieMask":
                this.cookieMask = value;
                break;
            case "actions":
                this.actions = value;
                break;
            case "cookie":
                this.cookie = value;
                break;
            case "idleTimeoutSec":
                this.idleTimeoutSec = value;
                break;
            case "flags":
                this.flags = value;
                break;
            case "in_port":
                this.in_port = value;
                break;
            case "hardTimeoutSec":
                this.hardTimeoutSec = value;
                break;
            case "outGroup":
                this.outGroup = value;
                break;
            case "priority":
                this.priority = value;
                break;
            case "outPort":
                this.outPort = value;
                break;
            case "version":
                this.version = value;
                break;
            case "command":
                this.command = value;
                break;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSwitchAdd(String switchAdd) {
        this.switchAdd = switchAdd;
    }

    public String getSwitchAdd() {
        return switchAdd;
    }

    public String getName() {
        return name;
    }

    public String getCookieMask() {
        return cookieMask;
    }

    public String getActions() {
        return actions;
    }

    public String getCookie() {
        return cookie;
    }

    public String getIdleTimeoutSec() {
        return idleTimeoutSec;
    }

    public String getFlags() {
        return flags;
    }

    public String getIn_port() {
        return in_port;
    }

    public String getHardTimeoutSec() {
        return hardTimeoutSec;
    }

    public String getOutGroup() {
        return outGroup;
    }

    public String getPriority() {
        return priority;
    }

    public String getOutPort() {
        return outPort;
    }

    public String getVersion() {
        return version;
    }

    public String getCommand() {
        return command;
    }
}

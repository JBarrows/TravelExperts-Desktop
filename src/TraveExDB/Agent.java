package TraveExDB;

public class Agent {
    private int agentId;
    private String agtFirstName, agtLastName;


    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getAgtFirstName() {
        return agtFirstName;
    }

    public void setAgtFirstName(String agtFirstName) {
        this.agtFirstName = agtFirstName;
    }

    public String getAgtLastName() {
        return agtLastName;
    }

    public void setAgtLastName(String agtLastName) {
        this.agtLastName = agtLastName;
    }


    public Agent(int agentId, String agtFirstName, String agtLastName) {
        this.agentId = agentId;
        this.agtFirstName = agtFirstName;
        this.agtLastName = agtLastName;
    }

    @Override
    public String toString() {
        return agtFirstName+ " " +agtLastName;
    }
}

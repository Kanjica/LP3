
public class Order{

    public enum Priority{
        HIGH("high"), MEDIUM("medium"),LOW("low");

        private String type;

        private Priority(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
    private Long id;
    private String clientName;
    private Product product;
    private int amount;
    private Priority priority;
    private Long timeStampOfCreation;

    public Order(Long id, String clientName, Product product, int amount, Priority priority, Long timeStampOfCreation){
        this.id = id;
        this.clientName = clientName;
        this.product = product;
        this.amount = amount;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Long getTimeStampOfCreation() {
        return timeStampOfCreation;
    }

}
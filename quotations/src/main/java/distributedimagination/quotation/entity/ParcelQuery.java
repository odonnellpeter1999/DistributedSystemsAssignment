package distributedimagination.quotation.entity;

public class ParcelQuery {
    private Double weightKg;
    private Double lengthCm;
    private Double widthCm;
    private Double heightCm;

    public ParcelQuery(Double weightKg, Double lengthCm, Double widthCm, Double heightCm) {
        this.weightKg = weightKg;
        this.lengthCm = lengthCm;
        this.widthCm = widthCm;
        this.heightCm = heightCm;
    }
}
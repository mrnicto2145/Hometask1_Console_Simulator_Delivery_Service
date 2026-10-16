package Address;

public class Address {
    private final double X, Y, regionFactor;

    public Address(double X,double Y,double regionFactor){
        this.X = X;
        this.Y = Y;
        this.regionFactor = regionFactor;
    }

    public double distance(Address destinAddress){
        double dxSquare = Math.pow(this.X - destinAddress.X, 2);
        double dySquare = Math.pow(this.Y - destinAddress.Y, 2);
        double regionDiffSquare = Math.pow(this.regionFactor - destinAddress.regionFactor, 2);
        return Math.sqrt(dxSquare + dySquare + regionDiffSquare);
    }

    public double getX() { return X;}
    public double getY() { return Y;}
    public double getRegionFactor() { return regionFactor;}

    public String toString() {
        return String.format("(x=%.2f, y=%.2f, регион=%.2f)", X, Y, regionFactor);
    }
}

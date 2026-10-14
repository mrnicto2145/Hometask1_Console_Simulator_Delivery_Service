package Address;

public class Address {
    double X, Y, regionFactor;

    public Address(double X,double Y,double regionFactor){
        this.X = X;
        this.Y = Y;
        this.regionFactor = regionFactor;
    }

    public double Distance(Address destinAddress){
        double dxSquare = Math.pow(this.X - destinAddress.X, 2);
        double dySquare = Math.pow(this.Y - destinAddress.Y, 2);
        double regionDiffSquare = Math.pow(this.regionFactor - destinAddress.regionFactor, 2);
        return Math.sqrt(dxSquare + dySquare + regionDiffSquare);
    }
}

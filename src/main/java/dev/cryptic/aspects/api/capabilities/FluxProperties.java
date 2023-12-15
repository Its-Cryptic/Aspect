package dev.cryptic.aspects.api.capabilities;

public class FluxProperties {
    private double volatility;
    private double density;

    // Constructor
    public FluxProperties(double volatility, double density) {
        this.volatility = volatility;
        this.density = density;
    }

    // Getter for volatility
    public double getVolatility() {
        return volatility;
    }

    // Setter for volatility
    public void setVolatility(double volatility) {
        this.volatility = volatility;
    }

    // Getter for density
    public double getDensity() {
        return density;
    }

    // Setter for density
    public void setDensity(double density) {
        this.density = density;
    }
}

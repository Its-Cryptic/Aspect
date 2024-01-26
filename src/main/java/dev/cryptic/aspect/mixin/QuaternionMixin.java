package dev.cryptic.aspect.mixin;

import com.mojang.math.Quaternion;
import dev.cryptic.aspect.api.util.MathUtility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Quaternion.class)
public class QuaternionMixin {
    @Shadow private float i;
    @Shadow private float j;
    @Shadow private float k;
    @Shadow private float r;

    /**
     * @author ItsCryptic
     * @reason Mojank never imnplemented this method, so I did it for them :)
     */
    @Overwrite
    public void slerp(Quaternion quaternion, float f) {
        float i1 = this.i;
        float j1 = this.j;
        float k1 = this.k;
        float w1 = this.r;

        float i2 = quaternion.i();
        float j2 = quaternion.j();
        float k2 = quaternion.k();
        float w2 = quaternion.r();

        if (i1 == i2 && j1 == j2 && k1 == k2 && w1 == w2) return;

        float result = (i1 * i2) + (j1 * j2) + (k1 * k2) + (w1 * w2);

        if (result < 0.0f) {
            // Negate the second quaternion and the result of the dot product
            i2 = -i2;
            j2 = -j2;
            k2 = -k2;
            w2 = -w2;
            result = -result;
        }

        // Set the first and second scale for the interpolation
        float scale0 = 1 - f;
        float scale1 = f;

        // Check if the angle between the 2 quaternions was big enough to
        // warrant such calculations
        if ((1 - result) > 0.1f) {
            // Get the angle between the 2 quaternions, and then store the sin()
            // of that angle
            float theta = MathUtility.acos(result);
            float invSinTheta = 1f / (float) Math.sin(theta);

            // Calculate the scale for q1 and q2, according to the angle and
            // its sine
            scale0 = (float) Math.sin((1 - f) * theta) * invSinTheta;
            scale1 = (float) Math.sin((f * theta)) * invSinTheta;
        }

        // Calculate the x, y, z and w values for the quaternion by using a
        // special
        // form of linear interpolation for quaternions.
        this.i = (scale0 * i1) + (scale1 * i2);
        this.j = (scale0 * j1) + (scale1 * j2);
        this.k = (scale0 * k1) + (scale1 * k2);
        this.r = (scale0 * w1) + (scale1 * w2);
    }

    public Quaternion slerp(Quaternion q1, Quaternion q2, float f) {
        float i1 = q1.i();
        float j1 = q1.j();
        float k1 = q1.k();
        float w1 = q1.r();

        float i2 = q2.i();
        float j2 = q2.j();
        float k2 = q2.k();
        float w2 = q2.r();

        if (i1 == i2 && j1 == j2 && k1 == k2 && w1 == w2) return q1;

        float result = (i1 * i2) + (j1 * j2) + (k1 * k2) + (w1 * w2);

        if (result < 0.0f) {
            // Negate the second quaternion and the result of the dot product
            i2 = -i2;
            j2 = -j2;
            k2 = -k2;
            w2 = -w2;
            result = -result;
        }

        // Set the first and second scale for the interpolation
        float scale0 = 1 - f;
        float scale1 = f;

        // Check if the angle between the 2 quaternions was big enough to
        // warrant such calculations
        if ((1 - result) > 0.1f) {
            // Get the angle between the 2 quaternions, and then store the sin()
            // of that angle
            float theta = MathUtility.acos(result);
            float invSinTheta = 1f / (float) Math.sin(theta);

            // Calculate the scale for q1 and q2, according to the angle and
            // its sine
            scale0 = (float) Math.sin((1 - f) * theta) * invSinTheta;
            scale1 = (float) Math.sin((f * theta)) * invSinTheta;
        }

        // Calculate the x, y, z and w values for the quaternion by using a
        // special
        // form of linear interpolation for quaternions.
        return new Quaternion((scale0 * i1) + (scale1 * i2), (scale0 * j1) + (scale1 * j2), (scale0 * k1) + (scale1 * k2), (scale0 * w1) + (scale1 * w2));
    }
}

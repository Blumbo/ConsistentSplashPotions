package net.blumbo.consistentpots.mixin;

import net.blumbo.consistentpots.ConsistentPots;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PotionEntity.class)
public class PotionEntityMixin {

    @Redirect(method = "applySplashPotion", at = @At(value = "INVOKE", target = "Ljava/lang/Math;sqrt(D)D"))
    private double modifyDistanceValue(double distanceSquared) {
        double extraAccuracy = ConsistentPots.accuracyPercentage / 100.0;
        if (extraAccuracy == 1.0) return 0;

        double distance = Math.sqrt(distanceSquared);
        return Math.max(0, (distance - extraAccuracy * 4.0) / (1.0 - extraAccuracy));
    }
}

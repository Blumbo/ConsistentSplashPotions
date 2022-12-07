package net.blumbo.consistentpots.mixin;

import net.blumbo.consistentpots.ConsistentPots;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftServer.class)

public class MinecraftServerMixin {

    @Inject(at = @At("HEAD"), method = "save")
    private void save(CallbackInfoReturnable<Boolean> cir) {
        ConsistentPots.save();
    }

}

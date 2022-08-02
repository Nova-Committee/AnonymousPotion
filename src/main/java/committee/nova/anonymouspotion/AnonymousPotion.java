package committee.nova.anonymouspotion;

import committee.nova.anonymouspotion.item.ItemInit;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnonymousPotion implements ModInitializer {
	public static final String MODID = "anonymouspotion";
	public static final Logger LOGGER = LoggerFactory.getLogger("Anonymous Potion");

	@Override
	public void onInitialize() {
		ItemInit.register();
		LOGGER.info("message from peakstep: i bet phoenixsc wont see this haha");
	}

	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}
}

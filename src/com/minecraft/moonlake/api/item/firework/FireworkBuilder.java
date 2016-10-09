package com.minecraft.moonlake.api.item.firework;

import org.bukkit.Color;
import org.bukkit.Location;

/**
 * <hr />
 * <div>
 *     <h1>Firework Builder Library</h1>
 *     <p>By Month_Light Ver: 1.0</p>
 * </div>
 * <hr />
 * <div>
 *     <h1>简单的 demo 使用例子</h1>
 *     <p>ItemLibraryFactorys.fireworkBuilder()</p>
 *     <p style="text-indent: 30px">.withType(FireworkType.STAR)</p>
 *     <p style="text-indent: 30px">.withColor(Color.RED)</p>
 *     <p style="text-indent: 30px">.withFadeRandom()</p>
 *     <p style="text-indent: 30px">.withFlicker()</p>
 *     <p style="text-indent: 30px">.withPower(3)</p>
 *     <p style="text-indent: 30px">.launch({@link Location}, 6)</p>
 *     <p>是不是很简单呀 (○｀ 3′○)</p>
 * </div>
 * <hr />
 *
 * @version 1.0
 * @author Month_Light
 */
public interface FireworkBuilder {

    FireworkBuilder withColor(Color color);

    FireworkBuilder withColor(int red, int green, int blue);

    FireworkBuilder withColorRandom();

    FireworkBuilder withType(FireworkType fireworkType);

    FireworkBuilder withPower(int power);

    FireworkBuilder withTrail();

    FireworkBuilder withTrail(boolean trail);

    FireworkBuilder withFlicker();

    FireworkBuilder withFlicker(boolean flicker);

    FireworkBuilder withFade(Color color);

    FireworkBuilder withFade(int red, int green, int blue);

    FireworkBuilder withFadeRandom();

    void launch(Location location);

    void launch(Location location, int amount);
}
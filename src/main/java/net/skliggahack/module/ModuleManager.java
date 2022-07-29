package net.skliggahack.module;

import net.skliggahack.module.modules.combat.*;
import net.skliggahack.module.modules.hud.SkliggaLogo;
import net.skliggahack.module.modules.hud.SkliggaVersionText;
import net.skliggahack.module.modules.misc.AutoCringe;
import net.skliggahack.module.modules.misc.NoLoadingScreen;
import net.skliggahack.module.modules.render.GammaOverride;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class ModuleManager
{
	private final HashMap<Class<? extends Module>, Module> modulesByClass = new HashMap<>();
	private final HashMap<String, Module> modulesByName = new HashMap<>();
	private final HashSet<Module> modules = new HashSet<>();

	public ModuleManager()
	{
		addModules();
	}

	public ArrayList<Module> getModules()
	{
		ArrayList<Module> arrayList = new ArrayList<>(modules);
		arrayList.sort(Comparator.comparing(Module::getName));
		return arrayList;
	}

	public Module getModule(Class<? extends Module> clazz)
	{
		return modulesByClass.get(clazz);
	}

	public Module getModuleByName(String name)
	{
		return modulesByName.get(name);
	}

	private void addModules()
	{
		addModule(AutoCringe.class);
		addModule(AutoDoubleHand.class);
		addModule(AutoInventoryTotem.class);
		addModule(AutoRekit.class);
		addModule(CwCrystal.class);
		addModule(GammaOverride.class);
		addModule(AutoLootYeeter.class);
		addModule(MarlowCrystal.class);
		addModule(NoLoadingScreen.class);
		//addModule(PlacementHighlight.class);
		addModule(SkliggaLogo.class);
		addModule(SkliggaVersionText.class);
		addModule(TriggerBot.class);
	}

	private void addModule(Class<? extends Module> clazz)
	{
		try
		{
			Module module = clazz.getConstructor().newInstance();
			modulesByClass.put(clazz, module);
			modulesByName.put(module.getName(), module);
			modules.add(module);
		}
		catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}
}

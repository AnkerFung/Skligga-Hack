package net.skliggahack.module.setting;

import net.skliggahack.gui.component.CheckboxComponent;
import net.skliggahack.gui.component.Component;
import net.skliggahack.gui.window.Window;
import net.skliggahack.module.Module;

import java.util.function.Supplier;

public class BooleanSetting extends Setting<Boolean>
{

	private boolean value;
	private final Supplier<Boolean> availability;

	private BooleanSetting(Builder builder)
	{
		super(builder.name, builder.description, builder.module);
		value = builder.value;
		availability = builder.availability;
	}

	@Override
	public Boolean get()
	{
		return value;
	}

	@Override
	public void set(Boolean value)
	{
		this.value = value;
	}

	@Override
	public Component makeComponent(Window parent)
	{
		return new CheckboxComponent(parent, 0, 0, value, v -> value = v, availability, getName());
	}

	public static class Builder
	{
		private String name;
		private String description;
		private Module module;
		private boolean value;
		private Supplier<Boolean> availability = () -> true;

		public static Builder newInstance()
		{
			return new Builder();
		}

		public BooleanSetting build()
		{
			return new BooleanSetting(this);
		}

		public Builder setName(String name)
		{
			this.name = name;
			return this;
		}

		public Builder setDescription(String description)
		{
			this.description = description;
			return this;
		}

		public Builder setModule(Module module)
		{
			this.module = module;
			return this;
		}

		public Builder setValue(boolean value)
		{
			this.value = value;
			return this;
		}

		public Builder setAvailability(Supplier<Boolean> availability)
		{
			this.availability = availability;
			return this;
		}
	}
}

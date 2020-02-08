package cn.epicfx.winfxk.donthitme.tool;

import java.util.ArrayList;
import java.util.List;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowSimple;

/**
 * @author Winfxk
 */
public class SimpleForm extends RootForm {
	private List<ElementButton> buttons = new ArrayList<>();
	private String Content = "";
	public List<String> Keys = new ArrayList<>();

	/**
	 *
	 * @param ID      表单ID
	 * @param Title   表单标题
	 * @param Content 表单内容
	 */
	public SimpleForm(int ID, String Title, String Content) {
		super(ID, Title);
		this.Content = Content;
	}

	/**
	 *
	 * @param ID      表单ID
	 * @param Title   表单标题
	 * @param Content 表单内容
	 */
	public SimpleForm(int ID, String Title, String Content, ButtonData... Buttons) {
		super(ID, Title);
		this.Content = Content;
		for (ButtonData data : Buttons)
			addButton(data);
	}

	/**
	 *
	 * @param ID      表单ID
	 * @param Title   表单标题
	 * @param Content 表单内容
	 */
	public SimpleForm(int ID, String Title, String Content, String... Buttons) {
		super(ID, Title);
		this.Content = Content;
		for (String string : Buttons)
			addButton(string);
	}

	@Override
	public FormWindow getFormWindow() {
		return new FormWindowSimple(Title, Content, buttons);
	}

	/**
	 * 添加一个按钮
	 *
	 * @param Text 按钮内容
	 * @return
	 */
	public SimpleForm addButton(String Text) {
		buttons.add(new ElementButton(Text));
		return this;
	}

	/**
	 *
	 * 添加一个按钮
	 *
	 * @param data 按钮数据
	 */
	public void addButton(ButtonData data) {
		addButton(data.Button, data.isLocal, data.Icon);
	}

	/**
	 * 添加一个按钮
	 *
	 * @param Text    按钮内容
	 * @param isLocal 是否为本地贴图
	 * @param Path    贴图路径
	 * @return
	 */
	public SimpleForm addButton(String Text, boolean isLocal, String Path) {
		if (Path == null || Path.isEmpty())
			return addButton(Text);
		buttons.add(new ElementButton(Text, new ElementButtonImageData(
				isLocal ? ElementButtonImageData.IMAGE_DATA_TYPE_PATH : ElementButtonImageData.IMAGE_DATA_TYPE_URL,
				Path)));
		return this;
	}

	/**
	 * 获取按钮数量
	 *
	 * @return
	 */
	public int getButtonSize() {
		return buttons.size();
	}

	/**
	 * 获取按钮列表
	 *
	 * @return
	 */
	public List<ElementButton> getButtons() {
		return buttons;
	}

	public static class ButtonData {
		private boolean isLocal = true;
		private String Button, Icon;
		/**
		 * 本地图片
		 */
		public static final boolean LocalImage = true;
		/**
		 * 网络图片
		 */
		public static final boolean UrlImage = false;

		/**
		 *
		 * @param Button 按钮文本
		 * @param Icon   按钮图标
		 */
		public ButtonData(String Button, String Icon) {
			this(Button, true, Icon);
		}

		/**
		 *
		 * @param Button 按钮文本
		 */
		public ButtonData(String Button) {
			this(Button, true, null);
		}

		/**
		 *
		 * @param Button  按钮文本
		 * @param isLocal 是否为本地资源
		 * @param Icon    按钮图标
		 */
		public ButtonData(String Button, boolean isLocal, String Icon) {
			this.Button = Button;
			this.Icon = Icon;
			this.isLocal = isLocal;
		}

		public void setButton(String button) {
			Button = button;
		}

		public void setIcon(String icon) {
			Icon = icon;
		}

		public void setLocal(boolean isLocal) {
			this.isLocal = isLocal;
		}

		public String getButton() {
			return Button;
		}

		public String getIcon() {
			return Icon;
		}

		public boolean isLocal() {
			return isLocal;
		}
	}
}

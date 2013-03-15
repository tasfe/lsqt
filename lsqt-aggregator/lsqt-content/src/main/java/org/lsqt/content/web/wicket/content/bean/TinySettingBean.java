package org.lsqt.content.web.wicket.content.bean;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.file.File;

import wicket.contrib.tinymce.TinyMceBehavior;
import wicket.contrib.tinymce.image.ImageUploadPanel;
import wicket.contrib.tinymce.settings.AdvListPlugin;
import wicket.contrib.tinymce.settings.AutoSavePlugin;
import wicket.contrib.tinymce.settings.Button;
import wicket.contrib.tinymce.settings.ContextMenuPlugin;
import wicket.contrib.tinymce.settings.DateTimePlugin;
import wicket.contrib.tinymce.settings.DirectionalityPlugin;
import wicket.contrib.tinymce.settings.EmotionsPlugin;
import wicket.contrib.tinymce.settings.FullScreenPlugin;
import wicket.contrib.tinymce.settings.IESpellPlugin;
import wicket.contrib.tinymce.settings.ImageUploadPlugin;
import wicket.contrib.tinymce.settings.MediaPlugin;
import wicket.contrib.tinymce.settings.PastePlugin;
import wicket.contrib.tinymce.settings.PreviewPlugin;
import wicket.contrib.tinymce.settings.PrintPlugin;
import wicket.contrib.tinymce.settings.SavePlugin;
import wicket.contrib.tinymce.settings.SearchReplacePlugin;
import wicket.contrib.tinymce.settings.TablePlugin;
import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.contrib.tinymce.settings.WordcountPlugin;

public class TinySettingBean
{

		public static void initSetting(TextArea textArea,Form form){
			TinyMCESettings settings = new TinyMCESettings(TinyMCESettings.Theme.advanced);

			// Register non-buttuon plugins
			settings.register(new ContextMenuPlugin());
			settings.register(new AutoSavePlugin());
			settings.register(new WordcountPlugin());
			settings.register(new AdvListPlugin());

			// first toolbar
			SavePlugin savePlugin = new SavePlugin();
			settings.add(savePlugin.getSaveButton(), TinyMCESettings.Toolbar.first,TinyMCESettings.Position.before);
			settings.add(Button.newdocument, TinyMCESettings.Toolbar.first,TinyMCESettings.Position.before);
			settings.add(Button.separator, TinyMCESettings.Toolbar.first,TinyMCESettings.Position.before);
			settings.add(Button.fontselect, TinyMCESettings.Toolbar.first,TinyMCESettings.Position.after);
			settings.add(Button.fontsizeselect, TinyMCESettings.Toolbar.first,TinyMCESettings.Position.after);

			// second toolbar
			PastePlugin pastePlugin = new PastePlugin();
			SearchReplacePlugin searchReplacePlugin = new SearchReplacePlugin();
			DateTimePlugin dateTimePlugin = new DateTimePlugin();
			dateTimePlugin.setDateFormat("Date: %m-%d-%Y");
			dateTimePlugin.setTimeFormat("Time: %H:%M");
			PreviewPlugin previewPlugin = new PreviewPlugin();
			settings.add(Button.cut, TinyMCESettings.Toolbar.second,TinyMCESettings.Position.before);
			settings.add(Button.copy, TinyMCESettings.Toolbar.second,TinyMCESettings.Position.before);
			settings.add(pastePlugin.getPasteButton(),TinyMCESettings.Toolbar.second, TinyMCESettings.Position.before);
			settings.add(pastePlugin.getPasteTextButton(),TinyMCESettings.Toolbar.second, TinyMCESettings.Position.before);
			settings.add(pastePlugin.getPasteWordButton(),TinyMCESettings.Toolbar.second, TinyMCESettings.Position.before);
			settings.add(Button.separator, TinyMCESettings.Toolbar.second,TinyMCESettings.Position.before);
			settings.add(searchReplacePlugin.getSearchButton(),TinyMCESettings.Toolbar.second, TinyMCESettings.Position.before);
			settings.add(searchReplacePlugin.getReplaceButton(),TinyMCESettings.Toolbar.second, TinyMCESettings.Position.before);
			settings.add(Button.separator, TinyMCESettings.Toolbar.second,TinyMCESettings.Position.before);
			settings.add(Button.separator, TinyMCESettings.Toolbar.second,TinyMCESettings.Position.after);
			settings.add(dateTimePlugin.getDateButton(),TinyMCESettings.Toolbar.second, TinyMCESettings.Position.after);
			settings.add(dateTimePlugin.getTimeButton(),TinyMCESettings.Toolbar.second, TinyMCESettings.Position.after);
			settings.add(Button.separator, TinyMCESettings.Toolbar.second,TinyMCESettings.Position.after);
			settings.add(previewPlugin.getPreviewButton(),TinyMCESettings.Toolbar.second, TinyMCESettings.Position.after);
			settings.add(Button.separator, TinyMCESettings.Toolbar.second,TinyMCESettings.Position.after);
			settings.add(Button.forecolor, TinyMCESettings.Toolbar.second,TinyMCESettings.Position.after);
			settings.add(Button.backcolor, TinyMCESettings.Toolbar.second,TinyMCESettings.Position.after);

			// third toolbar
			TablePlugin tablePlugin = new TablePlugin();
			EmotionsPlugin emotionsPlugin = new EmotionsPlugin();
			IESpellPlugin iespellPlugin = new IESpellPlugin();
			MediaPlugin mediaPlugin = new MediaPlugin();
			PrintPlugin printPlugin = new PrintPlugin();
			FullScreenPlugin fullScreenPlugin = new FullScreenPlugin();
			DirectionalityPlugin directionalityPlugin = new DirectionalityPlugin();
			settings.add(tablePlugin.getTableControls(),TinyMCESettings.Toolbar.third, TinyMCESettings.Position.before);
			settings.add(Button.separator, TinyMCESettings.Toolbar.third,TinyMCESettings.Position.after);
			settings.add(emotionsPlugin.getEmotionsButton(),TinyMCESettings.Toolbar.third, TinyMCESettings.Position.after);
			settings.add(iespellPlugin.getIespellButton(),TinyMCESettings.Toolbar.third, TinyMCESettings.Position.after);
			settings.add(mediaPlugin.getMediaButton(),TinyMCESettings.Toolbar.third, TinyMCESettings.Position.after);
			settings.add(Button.separator, TinyMCESettings.Toolbar.third,TinyMCESettings.Position.after);
			settings.add(printPlugin.getPrintButton(),TinyMCESettings.Toolbar.third, TinyMCESettings.Position.after);
			settings.add(Button.separator, TinyMCESettings.Toolbar.third,TinyMCESettings.Position.after);
			settings.add(directionalityPlugin.getLtrButton(),TinyMCESettings.Toolbar.third, TinyMCESettings.Position.after);
			settings.add(directionalityPlugin.getRtlButton(),TinyMCESettings.Toolbar.third, TinyMCESettings.Position.after);
			settings.add(Button.separator, TinyMCESettings.Toolbar.third,TinyMCESettings.Position.after);
			settings.add(fullScreenPlugin.getFullscreenButton(),TinyMCESettings.Toolbar.third, TinyMCESettings.Position.after);

			// other settings
			settings.setToolbarAlign(TinyMCESettings.Align.left);
			settings.setToolbarLocation(TinyMCESettings.Location.top);
			settings.setStatusbarLocation(TinyMCESettings.Location.bottom);
			settings.setResizing(true);
			
			//img upload settings
			
			String upImgs=((WebApplication)Session.get().getApplication()).getServletContext().getRealPath("/upload/img");
			File file=new File(upImgs);
			if(file.exists()==false)
			{
				file.mkdir();
			}
			
			
			ImageUploadPanel imageUploadPanel = new ImageUploadPanel("uploadPanel",file.getAbsolutePath());
			imageUploadPanel.setOutputMarkupPlaceholderTag(true);
			form.add(imageUploadPanel);
			
			ImageUploadPlugin plugin = new ImageUploadPlugin(imageUploadPanel.getImageUploadBehavior());
			settings.add(plugin.getImageUploadButton(), TinyMCESettings.Toolbar.first,TinyMCESettings.Position.after);
			
			textArea.add(new TinyMceBehavior(settings));
			
			
		}
		
}

package org.lsqt.content.web.wicket.component.tab;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class MyTabbedPanel extends Panel {
	public static final String	TAB_PANEL_ID	= "panel";
	private String	whereAmI;
	private final List<MyAbstractTab>	tabs;

	public MyTabbedPanel( String id, List<MyAbstractTab> tabs )
	{
		super( id, new Model( new Integer( -1 ) ) );

		this.setOutputMarkupId( true );

		if ( tabs == null ) { throw new IllegalArgumentException( "argument [tabs] cannot be null" ); }

		this.tabs = tabs;

		final IModel tabCount = new AbstractReadOnlyModel()
		{
			private static final long	serialVersionUID	= 1L;

			public Object getObject()
			{
				return new Integer( MyTabbedPanel.this.tabs.size() );
			}
		};

		WebMarkupContainer tabsContainer = new WebMarkupContainer( "tabs-container" )
		{
			private static final long	serialVersionUID	= 1L;

			protected void onComponentTag( ComponentTag tag )
			{
				super.onComponentTag( tag );
				tag.put( "class", getTabContainerCssClass() );
			}
		};
		add( tabsContainer );

		// add the loop used to generate tab names
		tabsContainer.add( new Loop( "tabs", tabCount )
		{
			private static final long	serialVersionUID	= 1L;

			protected void populateItem( LoopItem item )
			{
				
				final int index = item.getIndex();

				final WebMarkupContainer titleLink = newLink( "link", index );

				titleLink.add( newTitle( "title", MyTabbedPanel.this.tabs.get( index ).getOngletTitle(), index ) );
				item.add( titleLink );

				Form form = new Form( "tabForm" );

				AjaxButton closeButton = new AjaxButton( "closeTab", form )
				{
					@Override
					protected void onSubmit( AjaxRequestTarget target, Form form )
					{
						removeTab( target, index );
					}
				};

				if ( !MyTabbedPanel.this.tabs.get( index ).isCanBeClosed() )
					closeButton.setVisible( false );

				form.add( closeButton );
				item.add( form );
			}

			protected LoopItem newItem( int iteration )
			{
				return newTabContainer( iteration );
			}

		} );

		this.whereAmI = this.tabs.get( 0 ).getOngletTitle();
	}

	protected LoopItem newTabContainer( int tabIndex )
	{
		return new LoopItem( tabIndex )
		{
			private static final long	serialVersionUID	= 1L;

			protected void onComponentTag( ComponentTag tag )
			{
				super.onComponentTag( tag );
				String cssClass = (String)tag.getAttribute( "class" );
				if ( cssClass == null )
				{
					cssClass = " ";
				}
				cssClass += " tab" + getIndex();

				if ( getIndex() == getSelectedTab() )
				{
					cssClass += " selected";
				}
				if ( getIndex() == getTabs().size() - 1 )
				{
					cssClass += " last";
				}
				tag.put( "class", cssClass.trim() );
			}

		};
	}

	protected void onBeforeRender()
	{
		super.onBeforeRender();
		if ( !hasBeenRendered() && getSelectedTab() == -1 )
		{
			// select the first tab by default
			setSelectedTab( 0 );
		}
	}

	protected String getTabContainerCssClass()
	{
		return "tab-row";
	}

	public final List<MyAbstractTab> getTabs()
	{
		return tabs;
	}

	protected Component newTitle( String titleId, String title, int index )
	{
		return new Label( titleId, title );
	}

	public void removeTab( AjaxRequestTarget target, int index )
	{
		String titleDeletedTab = this.tabs.get( index ).getOngletTitle();
		this.tabs.remove( index );

		if ( titleDeletedTab.equals( this.whereAmI ) )
		{
			this.setSelectedTab( 0 );
			this.whereAmI = this.tabs.get( 0 ).getOngletTitle();
		}
		else
		{
			this.setSelectedTab( this.findSelectedTab( whereAmI ) );
		}

		target.add( this );
	}

	protected int findSelectedTab( String title )
	{
		int result = 0;
		for ( int i = 0 ; i < this.tabs.size() ; i++ )
			if ( title.equals( this.tabs.get( i ).getOngletTitle() ) )
				result = i;
		return result;
	}

	protected WebMarkupContainer newLink( String linkId, final int index )
	{
		return new Link( linkId )
		{
			public void onClick()
			{
				setSelectedTab( index );
			}
		};
	}

	public final void setSelectedTab( int index )
	{
		if ( index < 0 || index >= tabs.size() ) { throw new IndexOutOfBoundsException(); }
		setDefaultModelObject( new Integer( index ) );

		ITab tab = (ITab)tabs.get( index );
		Panel panel = (Panel) tab.getPanel( TAB_PANEL_ID );

		if ( panel == null ) { throw new WicketRuntimeException( "ITab.getPanel() returned null. TabbedPanel [" + getPath()
				+ "] ITab index [" + index + "]" );

		}

		if ( !panel.getId().equals( TAB_PANEL_ID ) ) { throw new WicketRuntimeException(
				"ITab.getPanel() returned a panel with invalid id [" + panel.getId()
						+ "]. You must always return a panel with id equal to the provided panelId parameter. TabbedPanel [" + getPath()
						+ "] ITab index [" + index + "]" ); }

		if ( get( TAB_PANEL_ID ) == null )
		{
			add( panel );
		}
		else
		{
			replace( panel );
		}

		this.whereAmI = this.tabs.get( index ).getOngletTitle();
	}

	public final int getSelectedTab()
	{
		return ( (Integer)getDefaultModelObject() ).intValue();
	}
}

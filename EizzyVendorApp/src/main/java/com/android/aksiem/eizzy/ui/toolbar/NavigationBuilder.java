package com.android.aksiem.eizzy.ui.toolbar;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.ui.toolbar.layoutfactory.LayoutFactory;
import com.android.aksiem.eizzy.ui.toolbar.menu.MenuActions;

import java.util.ArrayList;
import java.util.List;

public abstract class NavigationBuilder<T extends NavigationBuilder<T>> {
    public static final int NO_NAV_ICON = -1;

    private final LayoutFactory layoutFactory;

    int toolbarTitleRes;
    CharSequence toolbarTitle;
    int toolbarSubtitleRes;
    CharSequence toolbarSubtitle;
    int toolbarNavIconRes;
    Drawable toolbarNavIcon;
    View.OnClickListener toolbarNavClickListener = null;
    private NavigationDefaults navigationDefaults;
    boolean includeBottomNavBar;
    boolean includeDrawerNav;
    boolean includeTopNavBar;

    List<Integer> menuRes = new ArrayList<>();
    MenuActions.Builder menuActions = new MenuActions.Builder();

    public NavigationBuilder(LayoutFactory layoutFactory, NavigationDefaults navigationDefaults) {
        this.layoutFactory = layoutFactory;
        this.navigationDefaults = navigationDefaults;
        this.includeBottomNavBar = navigationDefaults.includeBottomNavBar;
        this.includeDrawerNav = navigationDefaults.includeDrawerNav;
        this.includeTopNavBar = navigationDefaults.includeTopNavBar;
    }

    protected abstract T getThis();

    public abstract View createNavigationView(LayoutInflater inflater, @Nullable ViewGroup container, View viewAttachedFragment);

    protected LayoutFactory layoutFactory() {
        return layoutFactory;
    }

    public T toolbarTitleRes(int toolbarTitleRes) {
        this.toolbarTitleRes = toolbarTitleRes;
        return getThis();
    }

    public T toolbarTitle(CharSequence toolbarTitle) {
        this.toolbarTitle = toolbarTitle;
        return getThis();
    }

    public T toolbarSubtitleRes(int toolbarSubtitleRes) {
        this.toolbarSubtitleRes = toolbarSubtitleRes;
        return getThis();
    }

    public T toolbarSubtitle(CharSequence toolbarSubtitle) {
        this.toolbarSubtitle = toolbarSubtitle;
        return getThis();
    }

    public T toolbarNavIconRes(int toolbarLogoRes) {
        this.toolbarNavIconRes = toolbarLogoRes;
        return getThis();
    }

    public T toolbarNavIcon(Drawable toolbarLogo) {
        this.toolbarNavIcon = toolbarLogo;
        return getThis();
    }

    public T setToolbarNavClickListener(View.OnClickListener toolbarNavClickListener) {
        this.toolbarNavClickListener = toolbarNavClickListener;
        return getThis();
    }

    public T menuRes(int menuRes, MenuActions.MenuActionItem... items) {
        return menuRes(menuRes, new MenuActions.Builder(items));
    }

    public T menuRes(int menuRes, MenuActions.Builder menuBuilder) {
        this.menuRes.add(menuRes);
        this.menuActions.append(menuBuilder);
        return getThis();
    }

    public T menuRes(int menuRes, MenuActions menuActions) {
        this.menuRes.add(menuRes);
        this.menuActions.append(menuActions);
        return getThis();
    }

    public T includeBottomNavBar(boolean includeBottomNavBar) {
        this.includeBottomNavBar = includeBottomNavBar;
        return getThis();
    }

    public boolean isIncludeBottomNavBar() {
        return includeBottomNavBar;
    }


    public T includeTopNavBar(boolean includeTopNavBar) {
        this.includeBottomNavBar = includeTopNavBar;
        return getThis();
    }

    public boolean isIncludeDrawerNav() {
        return includeDrawerNav;
    }


    public T includeDrawerNav(boolean includeDrawerNav) {
        this.includeDrawerNav = includeDrawerNav;
        return getThis();
    }

    public boolean isIncludeTopNavBar() {
        return includeBottomNavBar;
    }

    public int getColor(Context context, @ColorRes final int resourceId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.getColor(context, resourceId);
        } else {
            return context.getResources().getColor(resourceId);
        }
    }

    protected String getToolbarTitle(Context context) {
        String title = null;
        if (toolbarTitleRes != 0) {
            title = context.getString(toolbarTitleRes);
        } else if (toolbarTitle != null) {
            title = toolbarTitle.toString();
        }
        return title;
    }

    protected String getToolbarSubTitle(Context context) {
        String subTitle = null;
        if (toolbarSubtitleRes != 0) {
            subTitle = context.getString(toolbarSubtitleRes);
        } else if (toolbarSubtitle != null) {
            subTitle = toolbarSubtitle.toString();
        }
        return subTitle;
    }

    @Nullable
    protected Drawable getToolbarNavIcon(Context context) {
        if (toolbarNavIconRes != 0) {
            return ContextCompat.getDrawable(context, toolbarNavIconRes);
        } else if (toolbarNavIcon != null) {
            return toolbarNavIcon;
        }
        return null;
    }

    protected void setupMenu(Toolbar toolbar) {
        Menu menu = toolbar.getMenu();
        if (menu != null) {
            menu.clear();
        }
        if (!menuRes.isEmpty()) {
            final MenuActions actions = menuActions.build();
            for (Integer menuRes : menuRes) {
                toolbar.inflateMenu(menuRes);
            }
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return actions.onMenuItemClick(item);
                }
            });
        }
    }
}
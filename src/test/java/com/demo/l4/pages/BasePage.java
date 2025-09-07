package com.demo.l4.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public abstract class BasePage {
	protected final Page page;

	protected BasePage(Page page) {
		this.page = page;
	}

	/** ناوبری به URL مورد نظر (با انتظار برای بارگذاری اولیه) */
	public void open(String url) {
		page.navigate(url);
		// می‌توانید یک انتظار عمومی اضافه کنید (مثلاً برای NetworkIdle) در صورت نیاز:
		page.waitForLoadState(LoadState.NETWORKIDLE);
	}

	/** کلیک امن: از Locator استفاده می‌کند تا auto-wait انجام شود. */
	protected void click(Locator locator) {
		locator.click(); // auto-wait: visible, enabled, stable
	}

	/** پرکردن امن فیلدهای ورودی با پاک‌سازی قبلی */
	protected void fill(Locator locator, String value) {
		locator.fill(value); // auto-wait داخلی برای editable بودن
	}

	/** انتظار برای قابل‌مشاهده بودن عنصر */
	protected void shouldBeVisible(Locator locator) {
		assertThat(locator).isVisible();
	}

	/** اعتبارسنجی عنوان (Regex-friendly) */
	public void shouldHaveTitle(String regex) {
		assertThat(page).hasTitle(java.util.regex.Pattern.compile(regex));
	}

	// کمکی‌های متداول برای خوانایی و DRY
	protected Locator $(String selector) {
		return page.locator(selector);
	}

	protected void shouldContainText(Locator locator, String text) {
		assertThat(locator).containsText(text);
	}
}

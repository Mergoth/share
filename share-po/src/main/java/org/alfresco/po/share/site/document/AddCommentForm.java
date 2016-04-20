package org.alfresco.po.share.site.document;

import org.alfresco.po.HtmlPage;
import org.openqa.selenium.By;

/**
 * @author Aliaksei Boole
 */
public class AddCommentForm extends AbstractCommentForm
{
    private final static String FORM_DIV_CSS = "div[id$='default-add-actual-form-container']";
    private final static By FORM_DIV = By.cssSelector(FORM_DIV_CSS);
    private final static By AVATAR = By.cssSelector(FORM_DIV_CSS + ">img");
    private final static By CANCEL_BUTTON = By.cssSelector(FORM_DIV_CSS + " span[class~='yui-reset-button']>span>button");
    private final static By SUBMIT_BUTTON = By.cssSelector(FORM_DIV_CSS + " span[class~='yui-submit-button']>span>button");


    public HtmlPage clickAddCommentButton()
    {
        click(SUBMIT_BUTTON);
        return getCurrentPage();
    }

    public HtmlPage clickCancelButton()
    {
        click(CANCEL_BUTTON);
        return getCurrentPage();
    }

    public boolean isDisplay()
    {
        return isElementDisplayed(FORM_DIV);
    }

    public boolean isAvatarDisplay()
    {
        return isElementDisplayed(AVATAR);
    }

    public boolean isButtonsEnable()
    {
        return super.isButtonsEnable(SUBMIT_BUTTON, CANCEL_BUTTON);
    }

    public HtmlPage selectAddCommentButton()
    {
        click(SUBMIT_BUTTON);
        return getCurrentPage();
    }
}

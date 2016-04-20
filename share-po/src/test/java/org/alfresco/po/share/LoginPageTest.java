package org.alfresco.po.share;

import org.alfresco.po.AbstractTest;
import org.alfresco.test.FailedTestListener;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * LoginPage process integration test
 * 
 * @author Michael Suzuki
 * @since 1.0
 */
@Listeners(FailedTestListener.class)
public class LoginPageTest extends AbstractTest
{
    /**
     * Log a user into Alfresco with valid credentials
     * and then logout
     * @throws Exception if error
     */

    @Test
    public void testLoginWithPost()
    {
        DashBoardPage dashboardPage = shareUtil.loginWithPost(driver, shareUrl, "admin", "admin").render();
        Assert.assertTrue(dashboardPage.isBrowserTitle("dashboard"));    
        SharePage pageResponse = dashboardPage.getNav().logout().render();
        Assert.assertTrue(pageResponse.isBrowserTitle("login"));
    }

    @Test
    public void loginAndLogout() throws Exception 
    {
        driver.navigate().to(shareUrl);
        LoginPage page = factoryPage.getPage(driver).render();
        Assert.assertTrue(page.isBrowserTitle("login"));
        Assert.assertFalse(page.hasErrorMessage());

        DashBoardPage dashboardPage = shareUtil.loginAs(driver, shareUrl, username, password).render();
        Assert.assertFalse(page.isBrowserTitle("login"));
        Assert.assertTrue(dashboardPage.isBrowserTitle("dashboard"));
        Assert.assertTrue(dashboardPage.isLoggedIn());
        LoginPage pageResponse = dashboardPage.getNav().logout().render();
        Assert.assertTrue(pageResponse.isBrowserTitle("login"));
    }
    
    /**
     * Verify that a logging in with a fake
     * user will not grant the user access
     * and redisplay the login panel.
     * @throws Exception if error
     */
    @Test
    public void loginWithFakeCredentials() throws Exception
    {
        driver.navigate().to(shareUrl);
        LoginPage page = resolvePage(driver).render();
        page.loginAs("fake-admin", "fake-password").render(); 
        Assert.assertTrue(page.isBrowserTitle("login"));
        Assert.assertTrue(page.hasErrorMessage());
        Assert.assertTrue(page.getErrorMessage().length() > 1);
    }
    
    @Test(dependsOnMethods = "loginWithFakeCredentials")
    public void checkCSRFToken() throws Exception
    {
        driver.navigate().to(shareUrl);
        shareUtil.loginAs(driver, shareUrl, username, password);
        String csrfToken1 = getCookieValue();
        Assert.assertNotNull(csrfToken1);
        driver.navigate().refresh();
        DashBoardPage dPage = factoryPage.getPage(driver).render();
        dPage.render();
        String csrfToken2 = getCookieValue();
        Assert.assertNotNull(csrfToken2);
    }
    
    /**
     * Helper method to extract cookie value
     * of Alfresco-CSRFToken
     * @return String token value
     */
    private String getCookieValue()
    {
        Cookie cookie = driver.manage().getCookieNamed("Alfresco-CSRFToken");
        if(cookie != null)
        {
            return cookie.getValue();
        }
        return "";
    }
}

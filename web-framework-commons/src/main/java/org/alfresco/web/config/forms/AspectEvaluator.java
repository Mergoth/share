package org.alfresco.web.config.forms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Evaluator that determines whether a given object has a particular aspect
 * applied.
 * 
 * @author Neil McErlean
 */
public class AspectEvaluator extends NodeMetadataBasedEvaluator
{
    protected static final String JSON_ASPECTS = "aspects";
    
    private static Log logger = LogFactory.getLog(AspectEvaluator.class);

    @Override
    protected Log getLogger()
    {
        return logger;
    }

    /**
     * This method checks if the specified condition is matched by any of the aspects
     * within the specified jsonResponse String.
     * 
     * @return true if any aspect matches the condition, else false.
     */
    @Override
    protected boolean checkJsonAgainstCondition(String condition, String jsonResponseString)
    {
        boolean result = false;
        try
        {
            JSONObject json = new JSONObject(new JSONTokener(jsonResponseString));
            Object aspectsObj = json.get(JSON_ASPECTS);
            if (aspectsObj instanceof JSONArray)
            {
                JSONArray aspectsArray = (JSONArray) aspectsObj;
                for (int i = 0; i < aspectsArray.length(); i++)
                {
                    String nextAspect = aspectsArray.getString(i);

                    if (condition.equals(nextAspect))
                    {
                        result = true;
                        break;
                    }
                }
            }
        } 
        catch (JSONException e)
        {
            if (getLogger().isWarnEnabled())
            {
                getLogger().warn("Failed to find aspects in JSON response from metadata service.", e);
            }
        }
        return result;
    }
}

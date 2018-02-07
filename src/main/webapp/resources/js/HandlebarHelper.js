Handlebars.registerHelper('isFeedCheckType', function(feedType, type, options)
{
    if(feedType == type)
    {
        return options.fn(this);
    }
    else
    {
        return options.inverse(this);
    }
});

Handlebars.registerHelper('isFeedLikeOrCmnts', function(feedType, options)
{
    if(feedType == "like" || feedType == "comments")
    {
        return options.fn(this);
    }
    else
    {
        return options.inverse(this);
    }
});

Handlebars.registerHelper('isCommFeedOrLikeOrCmnts', function(feedType, options)
{
    if(feedType == "like" || feedType == "comments" || feedType == "community")
    {
        return options.fn(this);
    }
    else
    {
        return options.inverse(this);
    }
});

Handlebars.registerHelper('renderUpdatedKeys', function(updatedKeys)
{
    var htmlStr = "", updatedKeysLen = updatedKeys.length;
    htmlStr += "<span class='feed-updated-keys-val'>";
    for(i = 0;i < updatedKeysLen;i++)
    {
        if(i == 0)
        {
            htmlStr += updatedKeys[i];
        }
        else if(i > updatedKeysLen - 1)
        {
            htmlStr += " , " + updatedKeys[i];
        }
        else
        {
            htmlStr += " and " + updatedKeys[i];
        }
    }
    htmlStr += "</span>"
    
    return htmlStr;
});

Handlebars.registerHelper('renderUpdatedValues', function(updatedKeys, updatedValues)
{
    var htmlStr = "", updatedValuesLen = updatedValues.length, isImgPresent = false;
    if(updatedKeys[0] == "profile picture")
    {
        isImgPresent = true;
    }
    else if(updatedKeys[0] == "cover picture")
    {
        isImgPresent = true;
    }
    else if(updatedKeys[0] == "picture")
    {
        isImgPresent = true;
    }
    if(isImgPresent)
    {
        htmlStr += "<img class='connUs-feed-img' src='" + updatedValues[0] + "' alt='" + updatedKeys[0] + "' data-image='" + updatedValues[0] + "' data-description='" + updatedKeys[0] + "'>";
    }
    else
    {
        htmlStr += "<span class='feed-as-label'>as </span>";
    }
    if(!isImgPresent)
    {
        htmlStr += "<span class='feed-updated-keys-val'>";
        for(i = 0;i < updatedValuesLen;i++)
        {
            if(i == 0)
            {
                htmlStr += updatedValues[i];
            }
            else if(i > updatedValuesLen - 1)
            {
                htmlStr += " , " + updatedValues[i];
            }
            else
            {
                htmlStr += " and " + updatedValues[i];
            }
        }
        htmlStr += "</span>";
    }
    
    return htmlStr;
});
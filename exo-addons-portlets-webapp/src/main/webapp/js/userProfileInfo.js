/**
 * Created by Esslem on 01/05/2014.
 */

function onLoadProfile(data) {
    this.viewer = data.get('viewer').getData();

    var hostName = viewer.getField('hostName');
    var portalName = viewer.getField('portalName');
    var restContext = viewer.getField('restContextName');
    var address = window.top.location.href;
    var baseContext = hostName + "/" + portalName + "/";
    var extensionContext = address.replace(baseContext, "");
    var extensionParts = extensionContext.split("/");
    var context = baseContext + extensionParts[0] + "/" + extensionParts[1];

    var profileTempUrl = this.viewer.getField(opensocial.Person.Field.PROFILE_URL);
    var eXoUserID = profileTempUrl.substr(profileTempUrl.lastIndexOf('/') + 1);

    var profileUrl = context + '/profile/' + eXoUserID;
    var activitiesUrl = context + '/activities';
    var profileName = viewer.getDisplayName();
    var profileThumb = this.viewer.getField(opensocial.Person.Field.THUMBNAIL_URL);


    var prefs = new gadgets.Prefs();
    var linkmodifylabel = prefs.getMsg("modifylink");

    var html = new Array();
    html.push('<div class="ProfilePicture">',
            '<a href="' + activitiesUrl + '" target="_parent"><img class="GadCont ProfilePicture" src="' + profileThumb + '"/></a></div>',
            '<div class="GadCont ProfileInfo"><a href="' + activitiesUrl + '" target="_parent">', profileName, "</a><br/>",
            '<a id="ProfileLink" target="_parent" href="' + profileUrl + '">' + linkmodifylabel + '</a></div>');

    document.getElementById('Profil').innerHTML = html.join('');
}

function init() {
    loadProfile();
}

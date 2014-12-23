/*
 * Copyright (C) 2003-2014 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

function Favorites() {
    this.currentTitle = "";
    this.currentUrl = "";
    this.favorites = [];
    this.jzAddFavorite = "";
    this.jzGetFavorites = "";
    this.jzDeleteFavorite = "";

}

Favorites.prototype.init = function(jzAddFavorite, jzGetFavorites, jzDeleteFavorite) {
    this.jzAddFavorite = jzAddFavorite;
    this.jzGetFavorites = jzGetFavorites;
    this.jzDeleteFavorite = jzDeleteFavorite;
    this.overridePageTitle();
};

Favorites.prototype.overridePageTitle = function() {
    var title = document.title;
    var $breadcrumb = jQuery("ul.breadcrumb > li.active");
    if ($breadcrumb.length > 0) {
        title = $breadcrumb.html();
    }
    $breadcrumb = jQuery("ul.breadcrumb > li.active > a");
    if ($breadcrumb.length > 0) {
        title = $breadcrumb.html();
    }
    this.currentTitle = title;
    this.currentUrl = location.href;
};


Favorites.prototype.addFavorite = function() {

    jQuery.ajax({
        url: this.jzAddFavorite,
        data: {
            "title": this.currentTitle,
            "url": this.currentUrl
        },
        dataType: "json",
        context: this,
        success: function(data){
            console.log("Favorite added with: title="+this.currentTitle+" ; url="+this.contentUrl);
        },
        error: function () {
            console.log("Error when saving favorite");
        }
    });

};

Favorites.prototype.deleteFavorite = function(uuid) {

    jQuery.ajax({
        url: this.jzDeleteFavorite,
        data: {
            "uuid": uuid
        },
        dataType: "json",
        context: this,
        success: function(data){
            console.log("Favorite deleted with: uuid="+uuid);
        },
        error: function () {
            console.log("Error when deleting favorite");
        }
    });

};

Favorites.prototype.getFavorites = function(callback) {

    jQuery.ajax({
        url: this.jzGetFavorites,
        dataType: "json",
        context: this,
        success: function(data){
            this.favorites = data.favorites;
            console.log("you have "+this.favorites.length+" favorites");
            if (typeof callback === "function") {
                callback(this.favorites, this);
            }
        },
        error: function () {
            console.log("Error when getting favorites");
        }
    });

};

Favorites.prototype.loadFavorites = function(idDom) {
    var $container = jQuery(idDom);
    var html = "";
    this.getFavorites(function(myfavorites, context) {

        if (myfavorites.length>0) {
            for (var i=0 ; i<myfavorites.length ; i++) {
                var favorite = myfavorites[i];
                var title = favorite.title;
                if (title.length>35) title = title.substr(0, 34)+"...";
                html += "<li><a href='"+favorite.url+"' class='hover-action' style='line-height:22px;'><i class='"+context.getIcon(favorite.url)+"'></i>"+title;
                html += "<span class='actionHover' style='float:right;display:none;'><button class='btn btn-mini btn-delete-favorite' type='button' style='font-weight: bold;vertical-align: top;' data-uuid='"+favorite.id+"'>X</button></span>";
                html += "</a></li>";

            }
            html += "<li class='divider'>&nbsp;</li>";
        }
        $container.html(html);

        jQuery(".btn-delete-favorite").on("click", function(e) {
            var uuid = jQuery(this).attr("data-uuid");
            favorites.deleteFavorite(uuid);
            e.preventDefault();
        })

        jQuery("a.hover-action").hover(
            function() {
                $(this).children("span.actionHover").show();
            }, function() {
                $(this).children("span.actionHover").hide();
            }
        );

    });

};

Favorites.prototype.getIcon = function (url) {
    var appIcon = "uiIconFile";
    if (url.indexOf("/wiki")>0) {
        appIcon = "uiIconWiki";
    } else if (url.indexOf("/forum")>0) {
        appIcon = "uiIconUIForms";
    } else if (url.indexOf("/connexions")>0) {
        appIcon = "uiIconUser";
    } else if (url.indexOf("/documents")>0) {
        appIcon = "uiIconPLFDocuments";
    } else if (url.indexOf("/home")>0) {
        appIcon = "uiIconHome";
    }

    return appIcon;

};


var favorites = new Favorites();

(function($) {
    $(document).ready(function() {
        var $favoritesApp = $("#favorites-portlet");
        var jzAddFavorite = $favoritesApp.jzURL("Controller.addFavorite");
        var jzGetFavorites = $favoritesApp.jzURL("Controller.getFavorites");
        var jzDeleteFavorite = $favoritesApp.jzURL("Controller.deleteFavorite");

        favorites.init(jzAddFavorite, jzGetFavorites, jzDeleteFavorite);

        $(".favorites-add-link").on("click", function() {
            favorites.addFavorite();
        });

        $("#favorites-portlet").on("click", function() {

            favorites.loadFavorites("#bookmarks-details");

        });

    });

})(jQuery);

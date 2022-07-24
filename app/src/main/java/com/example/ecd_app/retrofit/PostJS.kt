package com.example.ecd_app.retrofit

import com.google.gson.annotations.SerializedName



data class PostJS (

    @SerializedName("id"            ) var id           : Int?           = null,
    @SerializedName("date"          ) var date         : String?        = null,
    @SerializedName("date_gmt"      ) var dateGmt      : String?        = null,
    @SerializedName("guid"          ) var guid         : Guid?          = Guid(),
    @SerializedName("modified"      ) var modified     : String?        = null,
    @SerializedName("modified_gmt"  ) var modifiedGmt  : String?        = null,
    @SerializedName("slug"          ) var slug         : String?        = null,
    @SerializedName("status"        ) var status       : String?        = null,
    @SerializedName("type"          ) var type         : String?        = null,
    @SerializedName("link"          ) var link         : String?        = null,
    @SerializedName("title"         ) var title        : Title?         = Title(),
    @SerializedName("content"       ) var content      : Content?       = Content(),
    @SerializedName("template"      ) var template     : String?        = null,
    @SerializedName("categories"    ) var categories   : ArrayList<Int> = arrayListOf(),
    @SerializedName("assign_users_" ) var assignUsers_ : ArrayList<Int> = arrayListOf(),
    @SerializedName("_links"        ) var Links        : Links?         = Links()

)

data class PostsJS (
    var posts :List<PostJS>? = null
)

data class Guid (

    @SerializedName("rendered" ) var rendered : String? = null

)

data class Title (

    @SerializedName("rendered" ) var rendered : String? = null

)

data class Content (

    @SerializedName("rendered"  ) var rendered  : String?  = null,
    @SerializedName("protected" ) var protected : Boolean? = null

)

data class Self (

    @SerializedName("href" ) var href : String? = null

)

data class Collection (

    @SerializedName("href" ) var href : String? = null

)

data class About (

    @SerializedName("href" ) var href : String? = null

)

data class wpAttachment (

    @SerializedName("href" ) var href : String? = null

)

data class wpTerm (

    @SerializedName("taxonomy"   ) var taxonomy   : String?  = null,
    @SerializedName("embeddable" ) var embeddable : Boolean? = null,
    @SerializedName("href"       ) var href       : String?  = null

)

data class Curies (

    @SerializedName("name"      ) var name      : String?  = null,
    @SerializedName("href"      ) var href      : String?  = null,
    @SerializedName("templated" ) var templated : Boolean? = null

)

data class Links (

    @SerializedName("self"          ) var self          : ArrayList<Self>          = arrayListOf(),
    @SerializedName("collection"    ) var collection    : ArrayList<Collection>    = arrayListOf(),
    @SerializedName("about"         ) var about         : ArrayList<About>         = arrayListOf(),
    @SerializedName("wp:attachment" ) var wpAttachment : ArrayList<wpAttachment> = arrayListOf(),
    @SerializedName("wp:term"       ) var wpTerm       : ArrayList<wpTerm>       = arrayListOf(),
    @SerializedName("curies"        ) var curies        : ArrayList<Curies>        = arrayListOf()
)
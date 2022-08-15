package com.example.ecd_app.retrofit

import com.google.gson.annotations.SerializedName


data class PostJS (

    @SerializedName("post"     ) var post     : RetrofitPost?   = RetrofitPost(),
    @SerializedName("category" ) var category : String? = null

)

data class RetrofitPost (

    @SerializedName("ID"                    ) var ID                  : Int?    = null,
    @SerializedName("post_author"           ) var postAuthor          : String? = null,
    @SerializedName("post_date"             ) var postDate            : String? = null,
    @SerializedName("post_date_gmt"         ) var postDateGmt         : String? = null,
    @SerializedName("post_content"          ) var postContent         : String? = null,
    @SerializedName("post_title"            ) var postTitle           : String? = null,
    @SerializedName("post_excerpt"          ) var postExcerpt         : String? = null,
    @SerializedName("post_status"           ) var postStatus          : String? = null,
    @SerializedName("comment_status"        ) var commentStatus       : String? = null,
    @SerializedName("ping_status"           ) var pingStatus          : String? = null,
    @SerializedName("post_password"         ) var postPassword        : String? = null,
    @SerializedName("post_name"             ) var postName            : String? = null,
    @SerializedName("to_ping"               ) var toPing              : String? = null,
    @SerializedName("pinged"                ) var pinged              : String? = null,
    @SerializedName("post_modified"         ) var postModified        : String? = null,
    @SerializedName("post_modified_gmt"     ) var postModifiedGmt     : String? = null,
    @SerializedName("post_content_filtered" ) var postContentFiltered : String? = null,
    @SerializedName("post_parent"           ) var postParent          : Int?    = null,
    @SerializedName("guid"                  ) var guid                : String? = null,
    @SerializedName("menu_order"            ) var menuOrder           : Int?    = null,
    @SerializedName("post_type"             ) var postType            : String? = null,
    @SerializedName("post_mime_type"        ) var postMimeType        : String? = null,
    @SerializedName("comment_count"         ) var commentCount        : String? = null,
    @SerializedName("filter"                ) var filter              : String? = null

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

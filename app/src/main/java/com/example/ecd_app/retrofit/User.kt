package com.example.ecd_app.retrofit

import com.google.gson.annotations.SerializedName

data class User (

    @SerializedName("id"             ) var id            : Int?                     = null,
    @SerializedName("name"           ) var name          : String?                  = null,
    @SerializedName("url"            ) var url           : String?                  = null,
    @SerializedName("description"    ) var description   : String?                  = null,
    @SerializedName("link"           ) var link          : String?                  = null,
    @SerializedName("slug"           ) var slug          : String?                  = null,
    @SerializedName("avatar_urls"    ) var avatarUrls    : AvatarUrls?              = AvatarUrls(),
    @SerializedName("meta"           ) var meta          : ArrayList<String>        = arrayListOf(),
    @SerializedName("assigned_posts" ) var assignedPosts : ArrayList<AssignedPosts> = arrayListOf(),
    @SerializedName("_links"         ) var Links         : LinksUser?                   = LinksUser()

)

data class AvatarUrls (

    @SerializedName("24" ) var twentyFour : String? = null,
    @SerializedName("48" ) var fortyEight : String? = null,
    @SerializedName("96" ) var ninetySix : String? = null

)

data class Category (

    @SerializedName("term_id"          ) var termId         : String? = null,
    @SerializedName("name"             ) var name           : String? = null,
    @SerializedName("slug"             ) var slug           : String? = null,
    @SerializedName("term_group"       ) var termGroup      : String? = null,
    @SerializedName("term_taxonomy_id" ) var termTaxonomyId : String? = null,
    @SerializedName("taxonomy"         ) var taxonomy       : String? = null,
    @SerializedName("description"      ) var description    : String? = null,
    @SerializedName("parent"           ) var parent         : String? = null,
    @SerializedName("count"            ) var count          : String? = null,
    @SerializedName("object_id"        ) var objectId       : String? = null,
    @SerializedName("term_order"       ) var termOrder      : String? = null,
    @SerializedName("pod_item_id"      ) var podItemId      : String? = null

)

data class AssignedPosts (

    @SerializedName("assign_users_"         ) var assignUsers_        : ArrayList<Int> = arrayListOf(),
    @SerializedName("ID"                    ) var ID                  : Int?                = null,
    @SerializedName("post_title"            ) var postTitle           : String?             = null,
    @SerializedName("post_content"          ) var postContent         : String?             = null,
    @SerializedName("post_excerpt"          ) var postExcerpt         : String?             = null,
    @SerializedName("post_author"           ) var postAuthor          : String?             = null,
    @SerializedName("post_date"             ) var postDate            : String?             = null,
    @SerializedName("post_date_gmt"         ) var postDateGmt         : String?             = null,
    @SerializedName("post_status"           ) var postStatus          : String?             = null,
    @SerializedName("comment_status"        ) var commentStatus       : String?             = null,
    @SerializedName("ping_status"           ) var pingStatus          : String?             = null,
    @SerializedName("post_password"         ) var postPassword        : String?             = null,
    @SerializedName("post_name"             ) var postName            : String?             = null,
    @SerializedName("to_ping"               ) var toPing              : String?             = null,
    @SerializedName("pinged"                ) var pinged              : String?             = null,
    @SerializedName("post_modified"         ) var postModified        : String?             = null,
    @SerializedName("post_modified_gmt"     ) var postModifiedGmt     : String?             = null,
    @SerializedName("post_content_filtered" ) var postContentFiltered : String?             = null,
    @SerializedName("post_parent"           ) var postParent          : Int?                = null,
    @SerializedName("guid"                  ) var guid                : String?             = null,
    @SerializedName("menu_order"            ) var menuOrder           : Int?                = null,
    @SerializedName("post_type"             ) var postType            : String?             = null,
    @SerializedName("post_mime_type"        ) var postMimeType        : String?             = null,
    @SerializedName("comment_count"         ) var commentCount        : String?             = null,
    @SerializedName("comments"              ) var comments            : Boolean?            = null,
    @SerializedName("category"              ) var category            : ArrayList<Category> = arrayListOf(),
    @SerializedName("id"                    ) var id                  : Int?                = null

)

data class LinksUser (

    @SerializedName("self"       ) var self       : ArrayList<Self>       = arrayListOf(),
    @SerializedName("collection" ) var collection : ArrayList<Collection> = arrayListOf()

)
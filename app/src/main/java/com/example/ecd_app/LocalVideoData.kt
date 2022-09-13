/**
 * @author Tinashe Mukundi Chitamba
 * data class for local video data
 */

package com.example.ecd_app

data class LocalVideoData(
    val VIDEO_NAME: String ?,
    val VIDEO_PATH: String,
    val VIDEO_TYPE: String,
    val VIDEO_DURATION: Long,
    val VIDEO_SIZE: Long,
    val VIDEO_DATE_ADDED: Long
) {

}

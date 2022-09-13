<?php
/**
* Plugin Name: UCT Portal Plugin
* Plugin URI: https://bhabhisana.org.za
* Description: Custom Endpoints for Mobile Application
* Version: 1.0
* Author: Jonathan Wayne Swanepoel
*/

/*
This file is stored within /wp-content/plugins/ecd-api/
and is accessible through SFTP. Contact Poln, their IT
provider, for these details.
*/

/*
Remove the admin bar
*/

function remove_admin_bar() {
  if (!current_user_can('administrator') && !is_admin()) {
    show_admin_bar(false);
  }
}

/*
Redirects all users to home page after login, instead
of wp-admin.
*/

function auto_redirect_after_logout(){
  wp_safe_redirect( home_url() );
  exit;
}

/*
Returns all public posts, and assigned posts to the
<username> input, with the category of each opost.
Returns a JSON response.
*/

function ecd_user( $slug ) {
	$emptyArray = [];
	if ( username_exists( $slug['slug'] ) ){
		// Get username as object
		$counter = 0;
		$user = get_user_by( 'login' , $slug['slug'] );
		$usersID = $user->ID;

		// Get assigned posts ID's
		$assignedPosts = get_user_meta($usersID,'assigned_posts',false);

		// Get public posts ID's
		$publicID = 8;
		$publicPosts = get_user_meta($publicID, 'assigned_posts', false);
		$allAssignedPosts = array_merge($assignedPosts,$publicPosts);

		// Unique Array
		$addedEntries = [];
		$addedEntriesPosts = [];

		// Loop through to remove redundent
		foreach ($allAssignedPosts as $assignedPost){
			foreach ($assignedPost as $post_){
				$exclude = false;
				if (in_array($post_, $addedEntries)){
					break;
				}
				else
				{
					$tags = [];
					$tags = get_the_tags($post_);
					if ( !empty($tags))
					{
						foreach($tags as $t)
						{
						// Exclude all non-enlgish content
						$current = $t->name;
						if (($current == "Afrikaans")||($current == "isiXhosa"))
						{
						$exclude = true;
						break;
						}
						}
					}
					if ($exclude){break;}
					$postType = get_post_type($post_);
						// only want ecd_content post types, nothing else
						if ( $postType == 'ecd_content')
						{
						// assign post node to array, potential to sort by date
						$categoryArray = get_the_category($post_);
						$postNode['post'] = get_post($post_);
						foreach( $categoryArray as $singleCat ) {
        						$postNode['category'] = $singleCat->name;
							// Only 1 category per post
							break;
						}
						$addedEntriesPosts[$counter] = $postNode;
						$addedEntries[$counter] = $post_;
						$counter ++;
						}
				}
			}
		}
		return $addedEntriesPosts;
	}
	return [];
}

/*
Returns a true/false response based on if the <username> exists
*/

function ecd_userAuth( $slug ) {
	$Array = [];
	if ( username_exists( $slug['slug'] ) ){
		$Array['response']=true;
		return $Array;
	}
	else{
		$Array['response']=false;
		return $Array;
	}
}

/*
Remove un-needed wp-admin menus for BBP Author
*/

function remove_admin_menu_core() {
// BBP Author ID is 12
  if (get_current_user_id()==12) {
  		remove_menu_page( 'edit.php' );                   //Posts
		remove_menu_page( 'edit.php?post_type=page' );    //Pages
		remove_menu_page( 'edit-comments.php' );          //Comments
		remove_menu_page( 'themes.php' );                 //Appearance
		remove_menu_page( 'plugins.php' );                //Plugins
		remove_menu_page( 'tools.php' );                  //Tools
		remove_menu_page( 'options-general.php' );        //Settings
		remove_menu_page( 'oxygen' );
		remove_menu_page( 'smush' );
  	}
}

/*
Remove un-needed wp-admin plugin menus for BBP Author
*/

function remove_admin_menu_plugins() {
	// BBP Author ID - 12
  if (get_current_user_id()==12) {
		remove_menu_page( 'ct_dashboard_page' );
		remove_menu_page( 'smush' );
		remove_menu_page( 'pods' );
		remove_menu_page( 'when-last-login-settings' );
  	}
}

/*
Function to add custom dashboard widgets
*/

function my_custom_dashboard_widgets() {
global $wp_meta_boxes;

wp_add_dashboard_widget('custom_button_widget', 'Quick Links', 'custom_button_help');

wp_add_dashboard_widget('custom_help_widget', 'ECD Portal Documentation', 'custom_dashboard_help');

}

/*
Function to add dashboard links and documentation
*/

function custom_dashboard_help() {
echo '<p>Welcome to the ECD Portal Help and Documentation! <br><br></p> <embed src="https://youandyourbaby.bhabhisana.org.za/wp-content/uploads/2022/08/Final-Bhabisana-ECD-Web-Portal-Documentation.pdf" width=100% height=450px type="application/pdf">';
}
function custom_button_help() {
echo '<br><a href="https://youandyourbaby.bhabhisana.org.za/wp-admin/post-new.php?post_type=ecd_content" style="margin-top: 24px; margin-right: 20px; border: solid #412E7D; padding-left: 12px; padding-right: 12px; padding-top: 6px; padding-bottom: 6px; background: #412E7D; color: white;">Create an ECD Content</a><a href="https://youandyourbaby.bhabhisana.org.za/wp-admin/edit.php?post_type=ecd_content"style="margin-top: 24px; padding-left: 12px; margin-right: 20px;padding-right: 12px; padding-top: 6px; padding-bottom: 6px; background: #412E7D; border: solid #412E7D; color: white;">View all ECD Content</a><br><br><br><a href="https://youandyourbaby.bhabhisana.org.za/wp-admin/user-new.php" style=" margin-top: 24px; margin-right: 20px; padding-left: 12px; padding-right: 12px; padding-top: 6px; padding-bottom: 6px; background: #152238; border: solid #152238; color: white;">Create a User<a/><a href="https://youandyourbaby.bhabhisana.org.za/wp-admin/users.php" style=" padding-left: 12px; margin-right: 20px; margin-top: 24px; padding-right: 12px; padding-top: 6px; padding-bottom: 6px; background: #152238; border: solid #152238; color: white;">View all Users<a/><br><br>';
}

/*
Hook into rest_api_int function and add rest routes
*/

add_action('rest_api_init', function() {

	register_rest_route( 'ecd/v1', 'usersPosts/(?P<slug>[a-zA-Z0-9-]+)', array(
		'methods' => 'GET',
		'callback' => 'ecd_user',
    ) );

	register_rest_route( 'ecd/v1', 'userAuth/(?P<slug>[a-zA-Z0-9-]+)', array(
		'methods' => 'GET',
		'callback' => 'ecd_userAuth',
    ) );

});

/*
Hook into wp_logout and redirect user to home page
*/

add_action('wp_logout','auto_redirect_after_logout');

/*
Hook into admin_menu and admin_init and remove wp-admin
plugin menus
*/

add_action( 'admin_menu', 'remove_admin_menu_core' );
add_action ('admin_init', 'remove_admin_menu_plugins');

/*
Add custom dashboard widgets
*/

add_action('wp_dashboard_setup', 'my_custom_dashboard_widgets');

/*
Removes the default wp admin bar, from all logged in
parents (users), except admins.
*/

add_action('after_setup_theme', 'remove_admin_bar');
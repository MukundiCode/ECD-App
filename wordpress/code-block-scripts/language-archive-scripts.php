<!--
*****
You and Your Baby - Templates > Tag (Language Archive)
Edit with Oxygen Builder
> Structure Panel
> Code Block 1
This code block deals with the: ECD Content Language Archive
*****
!-->

<?php
?><a href="/">Home ></a>
<?php echo " Filtered by Language ";

	if (is_user_logged_in()){
			$current_user = wp_get_current_user();
			$usersID = get_current_user_id();
			$users = get_user_meta($usersID,'assigned_posts',false);
	}
	else{
			$users = [];
			$usersID = "Current User: Guest";
	}

	$addedEntries = [];
	$publicID = 8;
	$public = get_user_meta($publicID, 'assigned_posts', false);
	$all_assigned = array_reverse(array_merge($users,$public));
	$current_tag =  single_tag_title("", false);

	foreach ($all_assigned as $user){
		# Loop through each Post and display or don't
		foreach ($user as $entry){
			# Get the Language of the Current Post ID
			$tags = get_the_tags($entry);
			$categories = get_the_category($entry);
			$separator = ' ';
			$output = '';
			$strict = false;
			$marker = false;
			$assignedCat = false;

			if ( ! empty( $categories ) ) {

			foreach( $categories as $category ) {

        			$output .= '<a href="' . esc_url( get_category_link( $category->term_id ) ) . '" alt="' . esc_attr( sprintf( __( 'View all posts in %s', 'textdomain' ), $category->name ) ) . '">' . esc_html( $category->name ) . '</a>' . $separator;
					if (! empty ($tags) ){
					# Check if Current Post ID is in the current language
					foreach($tags as $ta){
					if ($ta->name == $current_tag){
						# Can Display Post as it is in the current language
						$marker = true;
					}
					}
					}
					if ("Assigned Content"==$category->name){
						$assignedCat = true;
					}
					// else{echo "Not matched";}
    			}

			$cats = trim( $output, $separator );

			if (in_array($entry, $addedEntries))
			{
				break;
			}
			else{
				array_push($addedEntries, $entry);
			}
			# Remove Post as it is not in this language
			if (!$marker){break;}
			?>
			<a href="<?php echo esc_url( get_permalink($entry)) ?>">
			<div class="col-md-2" onclick="location.href='<?php echo esc_url( get_permalink($entry)) ?>';">
				<div class="underline">
				<?php esc_html_e(get_the_title($entry), 'textdomain')?>
				</div>
			<?php
			$postCat = trim( $output, $separator );
				# Assigned Content in Purple
				if ($assignedCat){
					?>
				<div class = "taxonomy-holder">
							<div class="small-private-cat">
							<?php echo trim( $output, $separator );?>
							</div>
							<div class="small-private-tag">
							<?php
								if ($tags){
								foreach($tags as $tag){
								?>
								<?php
								echo $tag->name . ' ';
								}
								}
								else{
									echo 'Tag';
								}
								?>
							</div>
						</div>

					<div class="read-more-private-button">
					<?php echo "Read More"?>
					</div>
					<?php
				}
				# Public Content in Green
				else{
					?>
					<div class = "taxonomy-holder">
							<div class="small-public-cat">
							<?php echo trim( $output, $separator );?>
							</div>
							<div class="small-public-tag">
							<?php
								if ($tags){
								foreach($tags as $tag){
								?>
								<?php
								echo $tag->name . ' ';
								}
								}
								else{
									echo 'Tag';
								}
								?>
							</div>
					</div>

					<div class="read-more-button">
					<?php echo "View Content"?>
					</div>
					<?php
				}
				?>
			</div>
			</a>
			<?php
			}
			if ($entry == $usersID){
				$show = True;
			}
			break;
		}
	}

?>
	<h1 class="headerh"> Browse Public Categories: </h1>
	<?php
	$categories = get_categories();
	$counter = 0;
	$current_category2 = single_cat_title("", false);
	foreach($categories as $category) {
			$current_category1 = $category->name;
			# Don't display Assigned Content if not logged in, otherwise all Public Categories
			if (($current_category1 == "Assigned Content") || ($current_category2 == $current_category1))	{
				echo "";
			 }
			else{
			# Display all public categories in green
			echo '<a class="public-cat" href="' . get_category_link($category->term_id) . '">' . $category->name . '</a>';
			}
			$counter ++;
	}

		if (is_user_logged_in()){
		# Assigned Content category
		foreach($categories as $category) {
				$current_category1 = $category->name;
				if (($current_category1 == "Assigned Content") && ($current_category2 != "Assigned Content"))	{
				?>
				<h1 class="headerh">Browse your Assigned Content:</h1>
				<?php
				# Display all private categories in purple
				echo '<a class="private-cat" href="' . get_category_link($category->term_id) . '">' . $category->name . '</a>';					 }
		}
		}

		else{
			echo "";
		}
?>
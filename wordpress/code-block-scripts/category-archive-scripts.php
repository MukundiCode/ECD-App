<!--
*****
You and Your Baby - Templates > Category Archive
Edit with Oxygen Builder
> Structure Panel
> Code Block 1
This code block deals with the: ECD Content Category Archive Top Heading
*****
!-->

<?php
$currentCategory = single_cat_title("", false);
if ($currentCategory != "Assigned Content"){
	?>
<div class="flex-small-public-cat"><img class ="padding-l" src="https://youandyourbaby.bhabhisana.org.za/wp-content/uploads/2022/08/baby.png" width="40px"/>
	<p class="padding-r ">Public Content</p>
</div>
<?php }
# Public Content
else{
	?>
	<div class="flex-small-private-cat">	<img class ="padding-l" src="https://youandyourbaby.bhabhisana.org.za/wp-content/uploads/2022/08/assigned.png" width="40px"/>
	<p class="padding-r ">Just for you</p>

</div>
<?php
}
echo "<br>";
?>
<!-- Breadcrumb !-->
<a href="/">Home ></a>
<?php echo " "; echo "Filtered by Category"; ?>

<!--
*****
You and Your Baby - Templates > Category Archive
Edit with Oxygen Builder
> Structure Panel
> Code Block 2
This code block deals with the: Display ECD Content per Category
*****
!-->

<?php
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


	$currentCategory = single_cat_title("", false);

	foreach ($all_assigned as $user){
		foreach ($user as $entry){
			# Get Entry (Post ID) Category and Tag
			$categories = get_the_category($entry);
			$separator = ' ';
			$output = '';
			$strict = false;
			$marker = false;
			$assignedCat = false;
			$tags = get_the_tags($entry);

			if ( ! empty( $categories ) ) {
			foreach( $categories as $category ) {
        			$output .= '<a href="' . esc_url( get_category_link( $category->term_id ) ) . '" alt="' . esc_attr( sprintf( __( 'View all posts in %s', 'textdomain' ), $category->name ) ) . '">' . esc_html( $category->name ) . '</a>' . $separator;
					if ($currentCategory == $category->name){
						// Current post matched to current category
						$marker = true;
					}
					if ("Assigned Content"==$category->name){
						// Assigned Content (Purple)
						$assignedCat = true;
					}
    			}

			$cats = trim( $output, $separator );

			# Remove duplicates if current post is in unique array
			if (in_array($entry, $addedEntries))
			{
				break;
			}
			# Add unique post
			else{
				array_push($addedEntries, $entry);
			}
			# Don't display post that isn't apart of the current category
			if (!$marker){break;}
			?>
			<a href="<?php echo esc_url( get_permalink($entry)) ?>">
			<div class="col-md-2" onclick="location.href='<?php echo esc_url( get_permalink($entry)) ?>';">
				<div class="underline">
				<?php esc_html_e(get_the_title($entry), 'textdomain')?>
				</div>
			<?php
			$postCat = trim( $output, $separator );
				# Assigned Content has purple content
				if ($assignedCat){
							?>
							<div class = "taxonomy-holder">
								<div class="small-private-cat">
								<?php echo trim( $output, $separator );?>
								</div>

								<?php
									if ( ! empty($tags)){
									?><div class="small-private-tag"><?php
									foreach($tags as $tag){
									?>
									<?php
									// Only one possible language, will not concatanate tags
									$outputTags = '<a href="' . esc_url( get_tag_link( $tag->term_id ) ) . '" alt="' . esc_attr( sprintf( __( 'View all posts in %s', 'textdomain' ), $tag->name ) ) . '">' . esc_html( $tag->name ) . '</a>' . $separator;
									echo trim( $outputTags, $seperator);
									}?>
									</div><?php
									}
									?>
							</div>

							<div class="read-more-private-button">
							<?php echo "Read More"?>
							</div>

							<?php
						}

				# Public content has green content
				else{
						?>
						<div class = "taxonomy-holder">
								<div class="small-public-cat">
								<?php echo trim( $output, $separator );?>
								</div>

								<?php
									if (! empty($tags)){
									?><div class="small-public-tag"><?php
									foreach($tags as $tag){
									?>
									<?php
									// Only one possible language, will not concatanate tags
									$outputTags = '<a href="' . esc_url( get_tag_link( $tag->term_id ) ) . '" alt="' . esc_attr( sprintf( __( 'View all posts in %s', 'textdomain' ), $tag->name ) ) . '">' . esc_html( $tag->name ) . '</a>' . $separator;
									echo trim( $outputTags, $seperator);
									}?>
									</div><?php
									}
									?>
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
			# Default true needed for posts displaying, break and move onto next post
			if ($entry == $usersID){
				$show = True;
			}
			break;
		}
	}

?>

<!--
*****
You and Your Baby - Templates > Category Archive
Edit with Oxygen Builder
> Structure Panel
> Code Block 3
This code block deals with the: Other Categories to Browse
*****
!-->

<h1 class="headerh"> Browse Public Categories: </h1>
<?php
$categories = get_categories();
$counter = 0;
# Current Archive Category Title
$currentCategory2 = single_cat_title("", false);
foreach($categories as $category) {
			# Don't display Assigned Content nor whichever category is currently being browsed
			$currentCategory = $category->name;
			if (($currentCategory == "Assigned Content") || ($currentCategory2 == $currentCategory))	{
				echo "";
			 }
			else{
			echo '<a class="public-cat" href="' . get_category_link($category->term_id) . '">' . $category->name . '</a>';
			}
			$counter ++;
	}

		if (is_user_logged_in()){
		# Display Assigned Content
		foreach($categories as $category) {
				$currentCategory = $category->name;
				if (($currentCategory == "Assigned Content") && ($currentCategory2 != "Assigned Content"))	{
				?>
				<h1 class="headerh">Browse your Assigned Content:</h1>
				<?php
				echo '<a class="private-cat" href="' . get_category_link($category->term_id) . '">' . $category->name . '</a>';					 }
		}
		}
		# Empty else
		else{
			echo "";
		}
?>
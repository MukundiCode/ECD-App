<!--
*****
You and Your Baby - Templates > Post Archive
Edit with Oxygen Builder
> Structure Panel
> Code Block 1
This code block deals with the: Hide/Show the Post Section based on if they are assigned
*****
!-->

<script type="text/javascript">
document.getElementById('section-2-61').style.display = 'unset';
</script>

<?php
	$users = get_post_meta(get_the_ID(),'assign_users_',false);
	$usersID = get_current_user_id();
	$ass = False;
	$show = False;
	$publicECD = False;

	if (is_user_logged_in()){
		$current_user = wp_get_current_user();
		echo "Current User: ";
		$usersname = esc_html( $current_user->user_login );
		echo $usersname;
	}
	else {
		echo "Current User: Guest";
	}

	foreach ($users as $user){
		foreach ($user as $entry){
			// echo $entry;
			if ($entry == $usersID){
				# Assigned to User
				$ass = True;
				# Can show the Post
				$show = True;
			}
			if ($entry == 8){
				# Can show the Post
				$show = True;
				# Assigned to Public
				$publicECD = True;
			}
			break;
		}
		echo "<br>";
	}
	# Public Resource
	if ($publicECD)
	{
		echo "This is a public resource for everyone to view.";
	}
	# Assigned Resource
	else
	{
		if (is_user_logged_in() and $ass){
			$current_user = wp_get_current_user();
			echo "This content is assigned to you, ";
			echo esc_html( $current_user->user_login );
			echo "<br>";
	}
	// Hide class #section-2-61 so no one can hardcode and view content if not a public ECD resource
	if (!$show){
	?>
	<script type="text/javascript">document.getElementById('section-2-61').style.display = 'none';</script>
	<?php
	}
	}
?>

<!--
*****
You and Your Baby - Templates > Post Archive
Edit with Oxygen Builder
> Structure Panel
> Code Block 2
This code block deals with the: Display Related ECD Content Post List (Public and Assigned Content)
*****
!-->

<?php
	$logged = false;
	if (is_user_logged_in()){
			$logged = true;
			$usersID = get_current_user_id();
			# Get Assigned Posts for Users ID
			$users = get_user_meta($usersID,'assigned_posts',false);
	}
	else{
			$users = [];
			$usersID = "Guest";
	}
	?>
	<div class="flex-category">
		<div class="filter-public-full">All</div>
		<?php
		if ($logged)
		{
			# Display Assigned Content filter only if User is logged in
			?>
			<a href="https://youandyourbaby.bhabhisana.org.za/category/assigned-content/"><div class="filter-private">Assigned Content</div></a>
			<?php
		}
		# Display the other filters
		?>
		<a href="https://youandyourbaby.bhabhisana.org.za/category/baby-development/"><div class="filter-public">Baby Development</div></a>
		<a href="https://youandyourbaby.bhabhisana.org.za/category/baby-health/"><div class="filter-public">Baby Health</div></a>
		<a href="https://youandyourbaby.bhabhisana.org.za/category/parent-health/"><div class="filter-public">Parent Health</div></a>
	</div>
	<?php

	# Merging Arrays of Posts
	$addedEntries = [];
	$publicID = 8;
	$assignedCat = false;
	# Array of Public Posts, i.e. Posts assigned to the Public User
	$public = get_user_meta($publicID,'assigned_posts',false);
	$allAssignedPosts = array_reverse(array_merge($public,$users));

	# allAssignedPosts is an array of Post ID's
	foreach ($allAssignedPosts as $user){
		$assignedCat = false;
		#user is the Post ID
		foreach ($user as $entry){

			# Get the Category
			$categories = get_the_category($entry);
			$separator = ' ';
			$output = '';

			# Get the Tag
			$tags = [];
			$tags = get_the_tags($entry);

			# Store Category Name
			if (! empty( $categories)) {
    		$marker = false;
			foreach( $categories as $category ) {
        			$postCatName = $category->name;
        			# This post is an Assigned Content Category (Purple)
					if ("Assigned Content"==$postCatName){
						$assignedCat = true;
					}
			}
			}
			# Remove Post's without Categories
			else{
				break;
			}
			# Remove any duplicates
			if (in_array($entry, $addedEntries))
			{
				break;
			}
			# Add unique post into array
			else{
				array_push($addedEntries, $entry);
			}

			# HTML and CSS styling of cards
			?>
			<!-- Make full card clickable to post permalink !-->
			<a href="<?php echo esc_url( get_permalink($entry));?>">

				<div class="col-md-2" onclick="location.href='<?php echo esc_url( get_permalink($entry)) ?>';">

					<!-- Underline Heading !-->
					<div class="underline">
					<?php esc_html_e(get_the_title($entry), 'textdomain');?>
					</div>

					<!-- Display Categories !-->
					<?php
						if ( ! empty( $categories ) ) {
							foreach( $categories as $category ) {
								// More than one possible category, will concatanate tags
								$output .= '<a href="' . esc_url( get_category_link( $category->term_id ) ) . '" alt="' . esc_attr( sprintf( __( 'View all posts in %s', 'textdomain' ), $category->name ) ) . '">' . esc_html( $category->name ) . '</a>' . $separator;
							}
						}
						# Assigned Content is in purple (private prefix in css classes)
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
							<!-- Display Categories !-->
							<div class="read-more-private-button">
							<?php echo "View Content"?>
							</div>

							<?php
						}

						# All other categories are in green (public prefix in css classes)
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
						<?php echo "View Content";?>
						</div>
						<?php
						}
						?>
					</div>
					</a>
					<?php
					}
			}

?>
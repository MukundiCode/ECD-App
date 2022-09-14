# You and Your Baby
The staff-facing web portal and user-facing web app is accessible through youandyourbaby.bhabhisana.org.za.

To view the web portal, the following usernames are available.

Username: UCTAdmin - this is the Bhabhisana Author view.

Username: UCTAdmin02 - this is UCT Admin view.

Passwords are available on request, or they are available within the ECD Hons MS Teams group.

## WordPress Scripts:
These scripts were used to achieve key functionality such as:
- Fetching posts assigned to a user.
- Designing and displaying these posts.
- Filtering by category or language.

These scripts are placed in the "code-blocks", within the WordPress installation.

Code-blocks are local. Therefore, these scripts cannot reference pre-defined variables or functions. Redundancy therefore exists throughout the independent code-blocks.

These scripts are used in:
- You and Your Baby Dashboard > Pages > Home.
- You and Your Baby Dashboard > Template > Category Archive.
- You and Your Baby Dashboard > Template > Language Archive.
- You and Your Baby Dashboard > Template > ECD Post Archive.

### WordPress CSS Scripts:
This (global) script, all-styles-script.css, was used to style the custom classes referenced throughout the code-blocks.

## ECD Plugin
This plugin, ecd-plugin.php, was developed through SFTP.

Key functionality includes:
- x2 Custom Endpoints for Mobile App to fetch.
- Web Portal customization for Authors.
- Web Portal online help and documentation.
- Small changes to native WP experience.

Should future iterations be made, please get in touch with Bhabhisanas IT provider for these details.

## Migration/Reusability Details for a new NGO:

Should you wish to implement this web-based ECD Content delivery system with a new NGO, follow the following steps on a new domain.

### WP All-in-one-Import

The no_media_youandyourbaby.wpress package must be imported into the target WP site through the All-in-One WP Migration plugin. Once this package has been imported, proceed to do the following:
- Save your permalinks twice via Settings > Permalinks.
- Resign your short codes via Oxygen > Settings > Security.
- Regenerate your CSS cache via Oxygen > Settings > CSS Cache.

This should result in all the styling, permalinks, and custom code-blocks to be in working order.

Thereafter, seeing as this installation has no media, export all existing media from the old WordPress site (tools > export > media), and upload accordingly to each post.

The documentation viewable through the web portal will show the new NGO how they can use the web portal. 

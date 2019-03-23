<?php

function send_dirlist (){

	$dir = "./savedGameStates/";	

	$a = scandir($dir);	
	$total = count($a); 
	$images = array(); 
	for($x = 0; $x < $total; $x++): 
		if ($a[$x] != '.' && $a[$x] != '..') { $images[] = $a[$x]; }	
	endfor;

	$images_length = count($images);

	for($i = 0; $i < $images_length; $i++){
		echo("   ".$i."  ==>  ".$images[$i]."\n");	
	}
	echo("EOF");
}


if ( $_GET['q'] == "dirlist" ){
	send_dirlist();
}
if ( $_GET['q'] == "writefile" ){
	

   $filename = "./savedGameStates/".$_GET['fajlnev'].".txt";
	echo ($filename);
 
   $file = fopen( $filename, "w" );
   
   if( $file == false ) {
      echo ( "Error in opening new file" );
      exit();
   }
   fwrite( $file, $_GET['content'] );
   fclose( $file );

}


?>



<?php

require_once "config.php";

if($_REQUEST['method'] == "verify"){
	$player_nick = $_GET['player_nick'];
	$code = $_GET['code'];
	$timestamp = $_GET['timestamp'];
	
	$sha256 = hash("sha256", $player_nick . "_" . $secret_key . "_" . $timestamp);
	if($code == $sha256){
		if((($timestamp / 1000) + 60) >= time()){
			/*
				User is verificated, check if the account wasn't claimed before,
				and redirect to the success page.
			*/
			$state = "SUCCESS";
		}
		else{
			$state = "EXPIRED_SESSION";
		}
	}
	else{
		$state = "INVALID_SIGN";
	}
	
	header("Location: $redirect?state=" . $state);
}

?>
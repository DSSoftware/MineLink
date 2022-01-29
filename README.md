# MineLink

### Supported Minecraft Versions: 1.12+

Plugin (Java) & Server-Side (PHP) implementation of linking Minecraft Accounts.

## Compatability
**MineLink is currently using 1.12 API.**

So, it can run on 1.12 and above.

However, you can recompile the sources, so it uses the newer API.

(Plugin is written without NMS or anything version-specific, so recompiling it for newer versions shouldn't create any issues.)

Tested Minecraft Versions: 1.17.1.

## Usage

On first start, plugin will create a default config.
### Configuration Steps:
1. Change your API URL in plugin config.
2. Change Secret Key.
3. **CHANGE FLAG "UNCONFIGURED" TO FALSE**
  * Else, plugin will work in Debug mode, sending all verification attempts to example.com
### Server Configuration
1. Change Secret Key in config.php
2. Set a valid Redirect URL
  * After a verification attempt, user will be redirected to a Redirect URL with a parameter *?state*
    * ?state=SUCCESS - user was successfully verified
    * ?state=EXPIRED_SESSION - user's session expired
    * ?state=INVALID_SIGN - the sign was forged

## License
This project is licensed under CC0.

## Contributing & Issues
When contributing changes to the project, please provide as much detail on the changes.
Please, if you found an issue, create a GitHub issue.

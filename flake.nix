{
  description = "Java NixOS flake for this project";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils, ... }:
    flake-utils.lib.eachDefaultSystem (system: let
      pkgs = nixpkgs.legacyPackages.${system};
      jdk = pkgs.jdk25;
      
      # The libraries Java needs to draw GUIs
      x11libs = with pkgs; [ 
        libX11 
        libXext 
        libXrender 
        libXtst 
        libXi 
      ];
      
    in {
      devShells.default = pkgs.mkShell {
        packages = [ 
          jdk 
          pkgs.jdt-language-server
        ];
        
        buildInputs = x11libs ++ [ pkgs.fontconfig ];
        
        shellHook = ''
          export JAVA_HOME=${jdk.home}
          export LD_LIBRARY_PATH=${pkgs.lib.makeLibraryPath x11libs}:$LD_LIBRARY_PATH

          # Create a stable symlink to the Nix JDK in the project root
          # This is the JDK that we want to be using for compilation
          # it is Java 25 for those that don't use NixOS/Nix
          ln -sfn ${jdk.home} .nix-jdk
          
        '';
      };
    });
}
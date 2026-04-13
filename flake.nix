# # # This is specifically for Sam because they use NixOS, if you don't, don't worry about this file :)
# # {
# #   description = "Java NixOS flake for this project";

# #   inputs = {
# #     nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
# #     flake-utils.url = "github:numtide/flake-utils";
# #   };

# #   outputs = {
# #     nixpkgs,
# #     flake-utils,
# #     ...
# #   }:
# #     flake-utils.lib.eachDefaultSystem (
# #       system: let
# #         pkgs = nixpkgs.legacyPackages.${system};

# #         nativeBuildInputs = with pkgs; [
# #           javaPackages.compiler.openjdk21
# #           jdt-language-server
# #           jdk
# #           libX11
# #           libXext
# #           libXrender
# #           libXtst
# #           libXi
# #         ];
# #         buildInputs = with pkgs; [];
# #       in {
# #         devShells.default = pkgs.mkShell {inherit nativeBuildInputs buildInputs;};
# #       }
# #     );
# # }
# {
#   outputs = { nixpkgs, flake-utils, ... }:
#     flake-utils.lib.eachDefaultSystem (system: let
#       pkgs = nixpkgs.legacyPackages.${system};
#       jdk = pkgs.jdk;
#       x11libs = with pkgs; [ libX11 libXext libXrender libXtst libXi ];
#     in {
#       devShells.default = pkgs.mkShell {
#         packages = [ jdk ];
#         buildInputs = x11libs;
#         shellHook = ''
#           export JAVA_HOME=${jdk.home}
#           export LD_LIBRARY_PATH=${pkgs.lib.makeLibraryPath x11libs}
#         '';
#       };
#     });
# }   

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
          # it is Java 25 for
          ln -sfn ${jdk.home} .nix-jdk
          
        '';
      };
    });
}
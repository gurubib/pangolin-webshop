COMPILER_FLAGS:=-O2 -o parser parser.cpp
# ImageMagick building configuration
MAGICK_CONFIG:=`Magick++-config --cxxflags --cppflags --ldflags --libs`
# Stack smaching protection
ENABLE_SSP:=-fstack-protector-all
# RELRO
ENABLE_FULL_RELRO:=-Wl,-z,relro,-z,now
#FORTIFY SOURCE
ENABLE_FORTIFY_SOURCE:=-D_FORTIFY_SOURCE=2

# POSITION DEPENDENT EXECUTION
ENABLE_PIE:=-pie
# NX bit protection
ENABLE_NX:=-z noexecstack

ENABLE_ALL_DEFENSE:=$(ENABLE_SSP) $(ENABLE_FULL_RELRO) $(ENABLE_FORTIFY_SOURCE) $(ENABLE_PIE) $(ENABLE_NX)

# ASLR
ENABLE_ASLR:=sudo sysctl -w kernel.randomize_va_space=2
parser: parser.cpp
	g++ $(COMPILER_FLAGS) $(ENABLE_ALL_DEFENSE) $(MAGICK_CONFIG)

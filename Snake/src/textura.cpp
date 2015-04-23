/*
 * textura.cpp
 *
 *  Created on: 09/03/2014
 *      Author: rafael
 */
#include "textura.h"

Textura::Textura(string file_name) {
	int img_width, img_height;
	unsigned char* img = SOIL_load_image(file_name.c_str(), &img_width, &img_height, NULL,0);
	glGenTextures(1, &texture_id);
	glBindTexture(GL_TEXTURE_2D, texture_id);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, img_width, img_height, 0, GL_RGB, GL_UNSIGNED_BYTE, img);
	SOIL_free_image_data(img);
}





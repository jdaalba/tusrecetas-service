package com.jdaalba.tusrecetasservice.dto;

import java.util.List;

public record ResultDTO<T>(List<T> data) {

}
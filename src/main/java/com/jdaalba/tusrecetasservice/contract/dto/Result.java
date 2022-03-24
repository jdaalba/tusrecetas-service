package com.jdaalba.tusrecetasservice.contract.dto;

import java.util.List;

public record Result<T>(List<T> data) {

}
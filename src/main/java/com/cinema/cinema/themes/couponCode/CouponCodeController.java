package com.cinema.cinema.themes.couponCode;

import com.cinema.cinema.themes.couponCode.model.CouponCode;
import com.cinema.cinema.themes.couponCode.model.CouponCodeInputDto;
import com.cinema.cinema.themes.couponCode.model.CouponCodeOutputDto;
import com.cinema.cinema.utils.DtoMapperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "Coupon codes")
@RequestMapping("/${app.prefix}/${app.version}/coupon-codes")
public class CouponCodeController {

    private final CouponCodeService couponCodeService;
    private final DtoMapperService mapperService;

    @GetMapping
    @Operation(summary = "Get all available coupon codes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with coupon codes returned"),
            @ApiResponse(responseCode = "204", description = "No coupon code found")})
    public ResponseEntity<List<CouponCodeOutputDto>> getAllCouponCodes() {
        List<CouponCode> couponCodes = couponCodeService.getAllAvailableCouponCodes();
        List<CouponCodeOutputDto> couponCodesDto = couponCodes.stream()
                .map(mapperService::mapToCouponCodeDto)
                .toList();
        HttpStatus status = couponCodesDto.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(couponCodesDto, status);
    }

    @GetMapping("/{couponCodeId}")
    @Operation(summary = "Get coupon code by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coupon code returned"),
            @ApiResponse(responseCode = "404", description = "Coupon code not found")})
    public ResponseEntity<CouponCodeOutputDto> getCouponCodeById(@PathVariable long couponCodeId) {
        CouponCode couponCode = couponCodeService.getCouponCode(couponCodeId);
        CouponCodeOutputDto couponCodeDto = mapperService.mapToCouponCodeDto(couponCode);
        return new ResponseEntity<>(couponCodeDto, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create coupon code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Coupon code created"),
            @ApiResponse(responseCode = "400", description = "Wrong data")})
    public ResponseEntity<Void> addCouponCode(@RequestBody CouponCodeInputDto couponCodeDto) {
        CouponCode couponCode = mapperService.mapToCouponCode(couponCodeDto);
        couponCode = couponCodeService.addCouponCode(couponCode);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{couponCodeId}")
                .buildAndExpand(couponCode.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}

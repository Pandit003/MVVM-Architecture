package com.example.mvvmarchitecture.constant

enum class EndpointConstants {
    None, LoginUserDTO, ProfileDTO, Inbound, PutAwayDTO, Inventory, Exception, CycleCount, Outbound, DenestingDTO, HouseKeepingDTO, ScanDTO, StockTakeDTO, PackingDTO, ValidateEanDTO, Transfers;

    enum class ScanType {
        Unloading, Putaway, Picking, Loading, DeNesting, Assortment
    }
}
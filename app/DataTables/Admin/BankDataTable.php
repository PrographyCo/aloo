<?php
namespace App\DataTables\Admin;

use App\Http\Controllers\Controller;
use App\Models\Bank;
use App\Models\City;
use App\Models\Driver;
use App\Models\Order;
use App\Models\Vendor;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Html\Editor\Editor;
use Yajra\DataTables\Html\Editor\Fields;
use Yajra\DataTables\Services\DataTable;

class BankDataTable extends DataTable
{

    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            });
    }

    public function query(Bank $model)
    {
        return $model
            ->newQuery();
    }

    public function html()
    {
        return $this->builder()
            ->setTableId('bank-table')
            ->columns($this->getColumns())
            ->minifiedAjax()
//            ->dom('Bfrtip')
            ->orderBy(1)
            ->buttons(
                Button::make('create'),
                Button::make('export'),
                Button::make('print'),
                Button::make('reset'),
                Button::make('reload')
            );
    }

    /**
     * Get columns.
     *
     * @return array
     */
    protected function getColumns()
    {
        return [
            Column::make('id'),
            Column::make('name_en')->title(__('web/admin/datatable.bank.name_en')),
            Column::make('name_ar')->title(__('web/admin/datatable.bank.name_ar')),
            Column::make('created_at')->title(__('web/admin/datatable.bank.created_at')),
        ];
    }

    /**
     * Get filename for export.
     *
     * @return string
     */
    protected function filename()
    {
        return 'bank_' . date('YmdHis');
    }
}

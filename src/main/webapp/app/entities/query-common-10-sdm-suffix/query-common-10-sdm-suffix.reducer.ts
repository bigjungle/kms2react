import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IQueryCommon10SdmSuffix, defaultValue } from 'app/shared/model/query-common-10-sdm-suffix.model';

export const ACTION_TYPES = {
  SEARCH_QUERYCOMMON10S: 'queryCommon10/SEARCH_QUERYCOMMON10S',
  FETCH_QUERYCOMMON10_LIST: 'queryCommon10/FETCH_QUERYCOMMON10_LIST',
  FETCH_QUERYCOMMON10: 'queryCommon10/FETCH_QUERYCOMMON10',
  CREATE_QUERYCOMMON10: 'queryCommon10/CREATE_QUERYCOMMON10',
  UPDATE_QUERYCOMMON10: 'queryCommon10/UPDATE_QUERYCOMMON10',
  DELETE_QUERYCOMMON10: 'queryCommon10/DELETE_QUERYCOMMON10',
  RESET: 'queryCommon10/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IQueryCommon10SdmSuffix>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type QueryCommon10SdmSuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: QueryCommon10SdmSuffixState = initialState, action): QueryCommon10SdmSuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_QUERYCOMMON10S):
    case REQUEST(ACTION_TYPES.FETCH_QUERYCOMMON10_LIST):
    case REQUEST(ACTION_TYPES.FETCH_QUERYCOMMON10):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_QUERYCOMMON10):
    case REQUEST(ACTION_TYPES.UPDATE_QUERYCOMMON10):
    case REQUEST(ACTION_TYPES.DELETE_QUERYCOMMON10):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_QUERYCOMMON10S):
    case FAILURE(ACTION_TYPES.FETCH_QUERYCOMMON10_LIST):
    case FAILURE(ACTION_TYPES.FETCH_QUERYCOMMON10):
    case FAILURE(ACTION_TYPES.CREATE_QUERYCOMMON10):
    case FAILURE(ACTION_TYPES.UPDATE_QUERYCOMMON10):
    case FAILURE(ACTION_TYPES.DELETE_QUERYCOMMON10):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_QUERYCOMMON10S):
    case SUCCESS(ACTION_TYPES.FETCH_QUERYCOMMON10_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_QUERYCOMMON10):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_QUERYCOMMON10):
    case SUCCESS(ACTION_TYPES.UPDATE_QUERYCOMMON10):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_QUERYCOMMON10):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/query-common-10-s';
const apiSearchUrl = 'api/_search/query-common-10-s';

// Actions

export const getSearchEntities: ICrudSearchAction<IQueryCommon10SdmSuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_QUERYCOMMON10S,
  payload: axios.get<IQueryCommon10SdmSuffix>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IQueryCommon10SdmSuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_QUERYCOMMON10_LIST,
    payload: axios.get<IQueryCommon10SdmSuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IQueryCommon10SdmSuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_QUERYCOMMON10,
    payload: axios.get<IQueryCommon10SdmSuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IQueryCommon10SdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_QUERYCOMMON10,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IQueryCommon10SdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_QUERYCOMMON10,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IQueryCommon10SdmSuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_QUERYCOMMON10,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
